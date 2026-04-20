export interface EndpointParam {
  name: string;
  label: string;
  type: 'number' | 'text' | 'date';
}

export interface EndpointDef {
  method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';
  path: string;
  label: string;
  params: EndpointParam[];
  bodyFields?: EndpointParam[];
  queryParams?: EndpointParam[];
}

export interface ModuleConfig {
  moduleName: string;
  owner: string;
  isMine: boolean;
  endpoints: EndpointDef[];
}

export const MODULE_CONFIGS: { [key: string]: ModuleConfig } = {

  // ─── AKSHA's MODULES ───
  agencies: {
    moduleName: 'Agency', owner: 'Aksha', isMine: false,
    endpoints: [
      { method: 'POST', path: '/agencies', label: 'Create Agency', params: [], bodyFields: [
        { name: 'name', label: 'Agency Name', type: 'text' },
        { name: 'details', label: 'Details', type: 'text' }
      ]},
      { method: 'GET', path: '/agencies', label: 'Get All Agencies', params: [] },
      { method: 'GET', path: '/agencies/{agencyId}', label: 'Get Agency by ID', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/agencies/{agencyId}', label: 'Update Agency', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Agency Name', type: 'text' },
        { name: 'details', label: 'Details', type: 'text' }
      ]},
      { method: 'DELETE', path: '/agencies/{agencyId}', label: 'Delete Agency', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ]}
    ]
  },

  'agency-offices': {
    moduleName: 'Agency Office', owner: 'Aksha', isMine: false,
    endpoints: [
      { method: 'POST', path: '/agencies/{agencyId}/offices', label: 'Create Office', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Office Name', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' }
      ]},
      { method: 'GET', path: '/agencies/{agencyId}/offices', label: 'Get Offices by Agency', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ]},
      { method: 'GET', path: '/offices/{officeId}', label: 'Get Office by ID', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/offices/{officeId}', label: 'Update Office', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Office Name', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' }
      ]},
      { method: 'DELETE', path: '/offices/{officeId}', label: 'Delete Office', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ]}
    ]
  },

  addresses: {
    moduleName: 'Address', owner: 'Aksha', isMine: false,
    endpoints: [
      { method: 'POST', path: '/addresses', label: 'Create Address', params: [], bodyFields: [
        { name: 'street', label: 'Street', type: 'text' },
        { name: 'city', label: 'City', type: 'text' },
        { name: 'state', label: 'State', type: 'text' },
        { name: 'zipCode', label: 'Zip Code', type: 'text' }
      ]},
      { method: 'GET', path: '/addresses/{addressId}', label: 'Get Address by ID', params: [
        { name: 'addressId', label: 'Address ID', type: 'number' }
      ]}
    ]
  },

  // ─── NITHISH's MODULES ───
  buses: {
    moduleName: 'Bus', owner: 'Nithish', isMine: false,
    endpoints: [
      { method: 'POST', path: '/offices/{officeId}/buses', label: 'Create Bus', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ], bodyFields: [
        { name: 'registrationNumber', label: 'Registration Number', type: 'text' },
        { name: 'totalSeats', label: 'Total Seats', type: 'number' },
        { name: 'busType', label: 'Bus Type', type: 'text' }
      ]},
      { method: 'GET', path: '/offices/{officeId}/buses', label: 'Get Buses by Office', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ]},
      { method: 'GET', path: '/buses/{busId}', label: 'Get Bus by ID', params: [
        { name: 'busId', label: 'Bus ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/buses/{busId}', label: 'Update Bus', params: [
        { name: 'busId', label: 'Bus ID', type: 'number' }
      ], bodyFields: [
        { name: 'registrationNumber', label: 'Registration Number', type: 'text' },
        { name: 'totalSeats', label: 'Total Seats', type: 'number' },
        { name: 'busType', label: 'Bus Type', type: 'text' }
      ]},
      { method: 'DELETE', path: '/buses/{busId}', label: 'Delete Bus', params: [
        { name: 'busId', label: 'Bus ID', type: 'number' }
      ]}
    ]
  },

  drivers: {
    moduleName: 'Driver', owner: 'Nithish', isMine: false,
    endpoints: [
      { method: 'POST', path: '/offices/{officeId}/drivers', label: 'Create Driver', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Driver Name', type: 'text' },
        { name: 'licenseNumber', label: 'License Number', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' }
      ]},
      { method: 'GET', path: '/offices/{officeId}/drivers', label: 'Get Drivers by Office', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ]},
      { method: 'GET', path: '/drivers/{driverId}', label: 'Get Driver by ID', params: [
        { name: 'driverId', label: 'Driver ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/drivers/{driverId}', label: 'Update Driver', params: [
        { name: 'driverId', label: 'Driver ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Driver Name', type: 'text' },
        { name: 'licenseNumber', label: 'License Number', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' }
      ]},
      { method: 'DELETE', path: '/drivers/{driverId}', label: 'Delete Driver', params: [
        { name: 'driverId', label: 'Driver ID', type: 'number' }
      ]}
    ]
  },

  // ─── AJITHA's MODULES ───
  'routes-mgmt': {
    moduleName: 'Route', owner: 'Ajitha', isMine: false,
    endpoints: [
      { method: 'POST', path: '/routes', label: 'Create Route', params: [], bodyFields: [
        { name: 'origin', label: 'Origin City', type: 'text' },
        { name: 'destination', label: 'Destination City', type: 'text' },
        { name: 'distance', label: 'Distance (km)', type: 'number' }
      ]},
      { method: 'GET', path: '/routes', label: 'Get All Routes', params: [] },
      { method: 'GET', path: '/routes/{routeId}', label: 'Get Route by ID', params: [
        { name: 'routeId', label: 'Route ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/routes/{routeId}', label: 'Update Route', params: [
        { name: 'routeId', label: 'Route ID', type: 'number' }
      ], bodyFields: [
        { name: 'origin', label: 'Origin City', type: 'text' },
        { name: 'destination', label: 'Destination City', type: 'text' },
        { name: 'distance', label: 'Distance (km)', type: 'number' }
      ]},
      { method: 'DELETE', path: '/routes/{routeId}', label: 'Delete Route', params: [
        { name: 'routeId', label: 'Route ID', type: 'number' }
      ]}
    ]
  },

  customers: {
    moduleName: 'Customer', owner: 'Ajitha', isMine: false,
    endpoints: [
      { method: 'POST', path: '/customers', label: 'Create Customer', params: [], bodyFields: [
        { name: 'name', label: 'Customer Name', type: 'text' },
        { name: 'email', label: 'Email', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' }
      ]},
      { method: 'GET', path: '/customers/{customerId}', label: 'Get Customer by ID', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/customers/{customerId}', label: 'Update Customer', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Customer Name', type: 'text' },
        { name: 'email', label: 'Email', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' }
      ]},
      { method: 'GET', path: '/customers', label: 'Get All Customers (Admin)', params: [] }
    ]
  },

  // ─── PRIYADHARSHINI's MODULES ───
  trips: {
    moduleName: 'Trip', owner: 'Priyadharshini', isMine: false,
    endpoints: [
      { method: 'POST', path: '/trips', label: 'Create Trip', params: [], bodyFields: [
        { name: 'busId', label: 'Bus ID', type: 'number' },
        { name: 'routeId', label: 'Route ID', type: 'number' },
        { name: 'departureTime', label: 'Departure Time', type: 'text' },
        { name: 'arrivalTime', label: 'Arrival Time', type: 'text' },
        { name: 'fare', label: 'Fare', type: 'number' }
      ]},
      { method: 'GET', path: '/trips', label: 'Get All Trips', params: [] },
      { method: 'GET', path: '/trips/{tripId}', label: 'Get Trip by ID', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]},
      { method: 'GET', path: '/trips/search', label: 'Search Trips', params: [], queryParams: [
        { name: 'fromCity', label: 'From City', type: 'text' },
        { name: 'toCity', label: 'To City', type: 'text' },
        { name: 'date', label: 'Date (YYYY-MM-DD)', type: 'text' }
      ]},
      { method: 'PUT', path: '/trips/{tripId}', label: 'Update Trip', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ], bodyFields: [
        { name: 'fare', label: 'Fare', type: 'number' }
      ]},
      { method: 'PATCH', path: '/trips/{tripId}/close', label: 'Close Trip', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]}
    ]
  },

  reviews: {
    moduleName: 'Review', owner: 'Priyadharshini', isMine: false,
    endpoints: [
      { method: 'POST', path: '/trips/{tripId}/reviews', label: 'Create Review', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ], bodyFields: [
        { name: 'customerId', label: 'Customer ID', type: 'number' },
        { name: 'rating', label: 'Rating (1-5)', type: 'number' },
        { name: 'comment', label: 'Comment', type: 'text' }
      ]},
      { method: 'GET', path: '/trips/{tripId}/reviews', label: 'Get Reviews by Trip', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]},
      { method: 'GET', path: '/customers/{customerId}/reviews', label: 'Get Reviews by Customer', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ]},
      { method: 'DELETE', path: '/reviews/{reviewId}', label: 'Delete Review', params: [
        { name: 'reviewId', label: 'Review ID', type: 'number' }
      ]}
    ]
  },

  // ─── VIGNESH's MODULES ───
  bookings: {
    moduleName: 'Booking', owner: 'Vignesh', isMine: true,
    endpoints: [
      { method: 'POST', path: '/trips/{tripId}/bookings', label: 'Book a Seat', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ], bodyFields: [
        { name: 'customerId', label: 'Customer ID', type: 'number' },
        { name: 'seatNumber', label: 'Seat Number', type: 'text' }
      ]},
      { method: 'GET', path: '/customers/{customerId}/bookings', label: 'Get Customer Bookings', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ]},
      { method: 'GET', path: '/bookings/{bookingId}', label: 'Get Booking by ID', params: [
        { name: 'bookingId', label: 'Booking ID', type: 'number' }
      ]},
      { method: 'PATCH', path: '/bookings/{bookingId}/cancel', label: 'Cancel Booking', params: [
        { name: 'bookingId', label: 'Booking ID', type: 'number' }
      ]},
      { method: 'GET', path: '/trips/{tripId}/seats/available', label: 'Get Available Seats', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]},
      { method: 'GET', path: '/trips/{tripId}/seats/booked', label: 'Get Booked Seats', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]}
    ]
  },

  payments: {
    moduleName: 'Payment', owner: 'Vignesh', isMine: true,
    endpoints: [
      { method: 'POST', path: '/payments', label: 'Create Payment', params: [], bodyFields: [
        { name: 'bookingId', label: 'Booking ID', type: 'number' },
        { name: 'amount', label: 'Amount', type: 'number' },
        { name: 'paymentMethod', label: 'Payment Method (CARD/CASH/UPI)', type: 'text' }
      ]},
      { method: 'GET', path: '/payments/{paymentId}', label: 'Get Payment by ID', params: [
        { name: 'paymentId', label: 'Payment ID', type: 'number' }
      ]},
      { method: 'GET', path: '/customers/{customerId}/payments', label: 'Get Customer Payments', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ]},
      { method: 'GET', path: '/bookings/{bookingId}/payment', label: 'Get Booking Payment', params: [
        { name: 'bookingId', label: 'Booking ID', type: 'number' }
      ]},
      { method: 'PATCH', path: '/payments/{paymentId}/status', label: 'Update Payment Status', params: [
        { name: 'paymentId', label: 'Payment ID', type: 'number' }
      ], bodyFields: [
        { name: 'status', label: 'Status (PAID/REFUNDED/PENDING)', type: 'text' }
      ]}
    ]
  }
};
