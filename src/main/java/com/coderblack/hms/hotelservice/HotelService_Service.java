package com.coderblack.hms.hotelservice;

import com.coderblack.hms.common.contraint.GuestHotelServiceStatus;
import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.common.response.SearchResponse;
import com.coderblack.hms.common.utils.GenericSearch;
import com.coderblack.hms.common.utils.SortDirection;
import com.coderblack.hms.exception.ConflictException;
import com.coderblack.hms.exception.NotFoundException;
import com.coderblack.hms.hotelservice.entity.OrderService;
import com.coderblack.hms.hotelservice.entity.HotelService;
import com.coderblack.hms.hotelservice.repository.GuestHotelServiceRepository;
import com.coderblack.hms.hotelservice.request.AddServiceRequest;
import com.coderblack.hms.hotelservice.request.OrderServiceRequest;
import com.coderblack.hms.hotelservice.request.UpdateServiceRequest;
import com.coderblack.hms.hotelservice.response.GuestHotelServiceResponse;
import com.coderblack.hms.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService_Service {
    private final HotelServiceRepository hotelServiceRepository;
    private final GuestHotelServiceRepository guestHotelServiceRepository;
    private final HotelServiceMapper mapper;
    private final GenericSearch<HotelService> genericSearch;

    public DefaultResponse addService(@Valid AddServiceRequest request) {
        var existed = this.hotelServiceRepository.findByName(request.name());
        if (existed.isPresent()) {
            throw new ConflictException(String.format("Hotel Service with NAME::%s already exist", request.name()));
        }
        HotelService hotelService = mapper.toAddService(request);
        var serviceId = this.hotelServiceRepository.save(hotelService).getId();
        return new DefaultResponse(
                "Successfully added service to services category",
                serviceId
        );
    }

    public DefaultResponse updateService(@Valid UpdateServiceRequest request, String hotelServiceId) {
        HotelService hotelService = this.hotelServiceRepository.findById(hotelServiceId).orElseThrow(() -> new NotFoundException(String.format("Hotel with this ID::%s not found", hotelServiceId)));
        var existed = this.hotelServiceRepository.findByName(request.name());
        if (existed.isPresent()) {
            throw new ConflictException(String.format("Hotel Service with NAME::%s already exist", request.name()));
        }
        HotelService service = this.mapper.toUpdateService(request, hotelService);
        var serviceId = this.hotelServiceRepository.save(service).getId();
        return new DefaultResponse(
                "Successfully update service in services category",
                serviceId
        );
    }


    public BaseResponse<HotelServiceResponse> getService(String hotelServiceId) {
        HotelServiceResponse hotelServiceResponse = this.hotelServiceRepository.findById(hotelServiceId).map(mapper::toResponse).orElseThrow(() -> new NotFoundException(String.format("Hotel with this ID::%s not found", hotelServiceId)));
        return new BaseResponse<>("Successfully fetched hotel service", hotelServiceResponse);
    }

    public PageResponse<HotelServiceResponse> getServices(String search, int page, int limit) {
        String[] serviceFields = {"name", "price", "serviceCategory"};

        SearchResponse<HotelService> hotelServices = genericSearch.search(search, HotelService.class, page, limit, serviceFields, null, null, null, null, null);
        List<HotelServiceResponse> response = hotelServices.results()
                .stream().map(mapper::toResponse).toList();
        int currentPage = page + 1;
        long totalCount = hotelServices.totalCount();
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        return new PageResponse<>(
                "Hotel Services Fetched Successfully.",
                currentPage,
                totalCount,
                totalPages,
                currentPage > 1,
                currentPage < totalPages,
                response
        );
    }

    public DefaultResponse deleteService(String hotelServiceId) {
        this.hotelServiceRepository.findById(hotelServiceId).orElseThrow(() -> new NotFoundException(String.format("Hotel Service with this ID::%s not found", hotelServiceId)));
        this.hotelServiceRepository.deleteById(hotelServiceId);
        return new DefaultResponse(
                "Successfully delete service in services category",
                hotelServiceId);
    }

    public DefaultResponse orderService(@Valid OrderServiceRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        HotelService hotelService = this.hotelServiceRepository.findById(request.hotelService()).orElseThrow(() -> new NotFoundException(String.format("Hotel Service with this ID::%s not found", request.hotelService())));
        OrderService guestService = OrderService.builder()
                .price(request.price())
                .details(request.details())
                .status(GuestHotelServiceStatus.PENDING)
                .hotelService(hotelService)
                .user(user)
                .build();

        OrderService guestHotelService = this.guestHotelServiceRepository.save(guestService);
        return new DefaultResponse(
                "Successfully ordered service",
                guestHotelService.getId()
        );
    }


    public DefaultResponse confirmOrderService(String orderServiceId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        OrderService guestHotelService = this.guestHotelServiceRepository.findByIdAndUser(orderServiceId, user).orElseThrow(() -> new NotFoundException(String.format("Guest Hotel Service with this ID::%s not found", orderServiceId)));
        guestHotelService.setStatus(GuestHotelServiceStatus.CONFIRMED);
        this.guestHotelServiceRepository.save(guestHotelService);
        return new DefaultResponse(
                "Successfully Confirmed ordered service",
                guestHotelService.getId()
        );
    }

    public DefaultResponse cancelOrderService(String orderServiceId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        OrderService guestHotelService = this.guestHotelServiceRepository.findByIdAndUser(orderServiceId, user).orElseThrow(() -> new NotFoundException(String.format("Guest Hotel Service with this ID::%s not found", orderServiceId)));
        guestHotelService.setStatus(GuestHotelServiceStatus.CANCELLED);
        this.guestHotelServiceRepository.save(guestHotelService);
        return new DefaultResponse(
                "Successfully Cancelled ordered service",
                guestHotelService.getId()
        );
    }


    public BaseResponse<GuestHotelServiceResponse> getGuestOrderedService(String orderServiceId, int zeroPage, int limit, String sortField, SortDirection sortDirection) {
        GuestHotelServiceResponse guestHotelService = this.guestHotelServiceRepository.findById(orderServiceId).map(mapper::toGuestOrderResponse).orElseThrow(() -> new NotFoundException(String.format("Guest Hotel Service with this ID::%s not found", orderServiceId)));

        return new BaseResponse<>(
                "Successfully fetched ordered service",
                guestHotelService
        );
    }


    public PageResponse<GuestHotelServiceResponse> getGuestOrderedServices(String search, int page, int limit, String sortField, SortDirection sortDirection) {
        String[] guestServiceFields = {"status", "price", "details", "createdAt", "updatedAt"};
        String[] joinFields = {"user", "hotelService"};
        String[] userJoinFields = {"firstName", "lastName", "email"};
        String[] hotelServiceJoinFields = {"name", "serviceCategory"};

        SearchResponse<OrderService> guestOrderServices;
        guestOrderServices = genericSearch.search(search, OrderService.class, page, limit, guestServiceFields, joinFields, userJoinFields, sortField, sortDirection, null);

        if (guestOrderServices.results().isEmpty()) {
            guestOrderServices = genericSearch.search(search, OrderService.class, page, limit, guestServiceFields, joinFields, hotelServiceJoinFields, sortField, sortDirection, null);
        }


        List<GuestHotelServiceResponse> response = guestOrderServices.results()
                .stream().map(mapper::toGuestOrderResponse).toList();
        int currentPage = page + 1;
        long totalCount = guestOrderServices.totalCount();
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        return new PageResponse<>(
                "Guest Ordered Services Fetched Successfully.",
                currentPage,
                totalCount,
                totalPages,
                currentPage > 1,
                currentPage < totalPages,
                response
        );
    }


    public PageResponse<GuestHotelServiceResponse> getMyOrderedServices(String search, int page, int limit, String sortField, SortDirection sortDirection, Authentication connectedUser) {

        var userId = ((User) connectedUser.getPrincipal()).getId();

        String[] guestServiceFields = {"status", "price", "details", "createdAt", "updatedAt"};
        String[] joinFields = {"user", "hotelService"};
        String[] hotelServiceJoinFields = {"name", "serviceCategory"};

        SearchResponse<OrderService> guestOrderServices = genericSearch.search(search, OrderService.class, page, limit, guestServiceFields, joinFields, hotelServiceJoinFields, sortField, sortDirection, userId);


        List<GuestHotelServiceResponse> response = guestOrderServices.results()
                .stream().map(mapper::toGuestOrderResponse).toList();
        int currentPage = page + 1;
        long totalCount = guestOrderServices.totalCount();
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        return new PageResponse<>(
                "Your Ordered Services Fetched Successfully.",
                currentPage,
                totalCount,
                totalPages,
                currentPage > 1,
                currentPage < totalPages,
                response
        );
    }

    public BaseResponse<GuestHotelServiceResponse> getOrderService(String orderServiceId) {
        GuestHotelServiceResponse response = this.guestHotelServiceRepository
                .findById(orderServiceId).map(mapper::toGuestOrderResponse).orElseThrow(() ->
                new NotFoundException(String.format("Order Service with ID::%s not found", orderServiceId)));
        return new BaseResponse<>("Successfully fetched order service", response);
    }
}
