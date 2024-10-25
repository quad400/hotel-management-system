package com.coderblack.hms.hotelservice;

import com.coderblack.hms.common.response.BaseResponse;
import com.coderblack.hms.common.response.DefaultResponse;
import com.coderblack.hms.common.response.PageResponse;
import com.coderblack.hms.common.utils.SortDirection;
import com.coderblack.hms.hotelservice.request.AddServiceRequest;
import com.coderblack.hms.hotelservice.request.OrderServiceRequest;
import com.coderblack.hms.hotelservice.request.UpdateServiceRequest;
import com.coderblack.hms.hotelservice.response.GuestHotelServiceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hotel-services")
public class HotelServiceController {

    private final HotelService_Service hotelService;

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @PostMapping("/add-service")
    public ResponseEntity<DefaultResponse> addService(
            @RequestBody @Valid AddServiceRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.hotelService.addService(request));
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @PutMapping("/{hotelServiceId}")
    public ResponseEntity<DefaultResponse> updateService(
            @RequestBody @Valid UpdateServiceRequest request,
            @PathVariable("hotelServiceId") String hotelServiceId

    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.hotelService.updateService(request, hotelServiceId));
    }

    @GetMapping("/{hotelServiceId}")
    public ResponseEntity<BaseResponse<HotelServiceResponse>> getService(
            @PathVariable("hotelServiceId") String hotelServiceId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.hotelService.getService(hotelServiceId));
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<HotelServiceResponse>> getGuestReservations(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) SortDirection sortDirection,
            Authentication connectedUser
    ) {
        int zeroPage = page - 1;
        return ResponseEntity.ok(hotelService.getServices(
                search, zeroPage, limit
        ));
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @DeleteMapping("/{hotelServiceId}")
    public ResponseEntity<DefaultResponse> deleteService(
            @PathVariable("hotelServiceId") String hotelServiceId

    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.hotelService.deleteService(hotelServiceId));
    }

    @PostMapping("/order-service")
    public ResponseEntity<DefaultResponse> orderService(
            @RequestBody @Valid OrderServiceRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.hotelService.orderService(request, connectedUser));
    }

    @PatchMapping("/confirm-order-service/{orderServiceId}")
    public ResponseEntity<DefaultResponse> confirmOrderService(
            @PathVariable("orderServiceId") String orderServiceId,
            Authentication connectedUser
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.hotelService.confirmOrderService(orderServiceId, connectedUser));
    }

    @PatchMapping("/cancel-order-service/{orderServiceId}")
    public ResponseEntity<DefaultResponse> cancelOrderService(
            @PathVariable("orderServiceId") String orderServiceId,
            Authentication connectedUser
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.hotelService.cancelOrderService(orderServiceId, connectedUser));
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @GetMapping("/guest-ordered-services")
    public ResponseEntity<PageResponse<GuestHotelServiceResponse>> getGuestOrderedServices(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) SortDirection sortDirection,
            Authentication connectedUser
    ) {
        int zeroPage = page - 1;
        return ResponseEntity.ok(hotelService.getGuestOrderedServices(
                search, zeroPage, limit, sortField,sortDirection
        ));
    }

    @GetMapping("/get-my-ordered-services")
    public ResponseEntity<PageResponse<GuestHotelServiceResponse>> getMyOrderedServices(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) SortDirection sortDirection,
            Authentication connectedUser
    ) {
        int zeroPage = page - 1;
        return ResponseEntity.ok(hotelService.getMyOrderedServices(
                search, zeroPage, limit, sortField,sortDirection,connectedUser
        ));
    }

    @GetMapping("/get-order-service/{orderServiceId}")
    public ResponseEntity<BaseResponse<GuestHotelServiceResponse>> getOrderService(
            @PathVariable("orderServiceId") String orderServiceId
    ){
        return ResponseEntity.ok(hotelService.getOrderService(orderServiceId));
    }
}
