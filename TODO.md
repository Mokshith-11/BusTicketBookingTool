# API Fixes Plan (No UI Changes)
Goal: Fix frontend API calls without modifying module-endpoints.component.html or any UI files.

## Priority 1: Payment Service URLs & Params (Confirmed Bugs)
- payment.service.ts: Wrong paths
  - getCustomerPayments: `/customers/${customerId}/payments` → `/payments/customers/${customerId}/payments`
  - getBookingPayment: `/bookings/${bookingId}/payment` → `/payments/bookings/${bookingId}/payment`
  - updatePaymentStatus: PATCH body → append `?status=${status}` (backend @RequestParam)

## Priority 2: Security for Shared Endpoints
- SecurityConfig.java: Add VIGNESH access to trip seats before /trips/**
  `.requestMatchers("/api/v1/trips/**/seats/**").hasAnyRole("VIGNESH", "PRIYA")`

## Priority 3: Service Path Fixes (Verify Others)
- agency.service.ts, route.service.ts, etc.: Confirm field names match DTOs
- Backend DTOs require missing fields, but UI bodyFields from config - update config if needed

## Steps:
✅ 1. payment.service.ts fixed (URLs + status param)

## Next:
✅ 2. SecurityConfig.java fixed - VIGNESH now accesses trip seats

3. Update MODULE_CONFIGS - add missing bodyFields for required DTO fields
4. Verify other services (route, customer, agency-office etc.)
5. Test all APIs

**Status:** Backend restart + payments config updated

**Remaining:** Add missing bodyFields to other modules (addresses, agency-offices, routes etc.), verify all services.

Test: vignesh login → payments → create (with customerId/paymentStatus), seats endpoints.


