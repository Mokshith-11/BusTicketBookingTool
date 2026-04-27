
export interface EndpointParam {
  name: string;
  label: string;
  type: 'number' | 'text' | 'date';
}

// EndpointDef is the blueprint for one API action shown in the module console.
export interface EndpointDef {
  method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';
  path: string;
  label: string;
  params: EndpointParam[];
  bodyFields?: EndpointParam[];
  queryParams?: EndpointParam[];
}

// ModuleConfig groups the API actions, owner, and description for one module card.
export interface ModuleConfig {
  moduleName: string;
  owner: string;
  isMine: boolean;
  description: string;
  endpoints: EndpointDef[];
}

// The UI is mostly data-driven: adding or editing an endpoint here changes what the module console renders.
export const MODULE_CONFIGS: { [key: string]: ModuleConfig } = {

  agencies: {
    moduleName: 'Agency', owner: 'Aksha', isMine: false,
    description: 'Master data ownership hierarchy (Insurer level).',
    endpoints: [
      { method: 'POST', path: '/agencies', label: 'Create Agency', params: [], bodyFields: [
        { name: 'name', label: 'Agency Name', type: 'text' },
        { name: 'contactPersonName', label: 'Contact Person', type: 'text' },
        { name: 'email', label: 'Email', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' }
      ]},
      { method: 'GET', path: '/agencies', label: 'Get All Agencies', params: [] },
      { method: 'GET', path: '/agencies/{agencyId}', label: 'Get Agency by ID', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/agencies/{agencyId}', label: 'Update Agency', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Agency Name', type: 'text' },
        { name: 'contactPersonName', label: 'Contact Person', type: 'text' },
        { name: 'email', label: 'Email', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' }
      ]},
      { method: 'DELETE', path: '/agencies/{agencyId}', label: 'Deactivate Agency', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ]}
    ]
  },

  'agency-offices': {
    moduleName: 'Agency Office', owner: 'Aksha', isMine: false,
    description: 'Branch hierarchy under primary master data.',
    endpoints: [
{ method: 'POST', path: '/agencies/{agencyId}/offices', label: 'Add Office', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ], bodyFields: [
        { name: 'officeMail', label: 'Office Email', type: 'text' },
        { name: 'officeContactPersonName', label: 'Contact Person', type: 'text' },
        { name: 'officeContactNumber', label: 'Contact Phone', type: 'text' },
        { name: 'officeAddressId', label: 'Address ID', type: 'number' }
      ]},
      { method: 'GET', path: '/agencies/{agencyId}/offices', label: 'List Offices by Agency', params: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' }
      ]},
      { method: 'GET', path: '/offices/{officeId}', label: 'Get Office Details', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/offices/{officeId}', label: 'Update Office', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ], bodyFields: [
        { name: 'agencyId', label: 'Agency ID', type: 'number' },
        { name: 'officeMail', label: 'Email', type: 'text' },
        { name: 'officeContactPersonName', label: 'Contact Person', type: 'text' },
        { name: 'officeContactNumber', label: 'Phone', type: 'text' },
        { name: 'officeAddressId', label: 'Address ID', type: 'number' }
      ]},
      { method: 'DELETE', path: '/offices/{officeId}', label: 'Deactivate Office', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ]}
    ]
  },

  addresses: {
    moduleName: 'Address', owner: 'Aksha', isMine: false,
    description: 'Shared reference for all physical location data.',
    endpoints: [
{ method: 'POST', path: '/addresses', label: 'Create Address', params: [], bodyFields: [
        { name: 'address', label: 'Address', type: 'text' },
        { name: 'city', label: 'City', type: 'text' },
        { name: 'state', label: 'State', type: 'text' },
        { name: 'zipCode', label: 'Zip Code', type: 'text' }
      ]},
      { method: 'GET', path: '/addresses/{addressId}', label: 'Get Address by ID', params: [
        { name: 'addressId', label: 'Address ID', type: 'number' }
      ]}
    ]
  },

  buses: {
    moduleName: 'Bus', owner: 'Nithish', isMine: false,
    description: 'Asset management and fleet specification control.',
    endpoints: [
      { method: 'POST', path: '/offices/{officeId}/buses', label: 'Register Bus', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ], bodyFields: [
        { name: 'registrationNumber', label: 'Reg Plate', type: 'text' },
        { name: 'capacity', label: 'Total Seats', type: 'number' },
        { name: 'type', label: 'Bus Type', type: 'text' }
      ]},
      { method: 'GET', path: '/offices/{officeId}/buses', label: 'List Buses by Office', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ]},
      { method: 'GET', path: '/buses/{busId}', label: 'Get Bus Details', params: [
        { name: 'busId', label: 'Bus ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/buses/{busId}', label: 'Update Bus', params: [
        { name: 'busId', label: 'Bus ID', type: 'number' }
      ], bodyFields: [
        { name: 'officeId', label: 'Office ID', type: 'number' },
        { name: 'registrationNumber', label: 'Plate', type: 'text' },
        { name: 'capacity', label: 'Seats', type: 'number' },
        { name: 'type', label: 'Type', type: 'text' }
      ]},
      { method: 'DELETE', path: '/buses/{busId}', label: 'Retire Bus', params: [
        { name: 'busId', label: 'Bus ID', type: 'number' }
      ]}
    ]
  },

  drivers: {
    moduleName: 'Driver', owner: 'Nithish', isMine: false,
    description: 'Workforce management and profile validations.',
    endpoints: [
      { method: 'POST', path: '/offices/{officeId}/drivers', label: 'Register Driver', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Full Name', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' },
        { name: 'licenseNumber', label: 'License #', type: 'text' },
        { name: 'addressId', label: 'Address ID', type: 'number' }
      ]},
      { method: 'GET', path: '/offices/{officeId}/drivers', label: 'List Drivers by Office', params: [
        { name: 'officeId', label: 'Office ID', type: 'number' }
      ]},
      { method: 'GET', path: '/drivers/{driverId}', label: 'Driver Profile', params: [
        { name: 'driverId', label: 'Driver ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/drivers/{driverId}', label: 'Update Driver', params: [
        { name: 'driverId', label: 'Driver ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Name', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' },
        { name: 'licenseNumber', label: 'License #', type: 'text' },
        { name: 'officeId', label: 'Office ID', type: 'number' },
        { name: 'addressId', label: 'Address ID', type: 'number' }
      ]},
      { method: 'DELETE', path: '/drivers/{driverId}', label: 'Remove Driver', params: [
        { name: 'driverId', label: 'Driver ID', type: 'number' }
      ]}
    ]
  },

  'routes-mgmt': {
    moduleName: 'Route', owner: 'Ajitha', isMine: false,
    description: 'Operational logic for policy plans and schedules.',
    endpoints: [
      { method: 'POST', path: '/routes', label: 'Create Route', params: [], bodyFields: [
        { name: 'fromCity', label: 'From City', type: 'text' },
        { name: 'toCity', label: 'To City', type: 'text' },
        { name: 'breakPoints', label: 'Break Points', type: 'number' },
        { name: 'duration', label: 'Duration (Hrs)', type: 'number' }
      ]},
      { method: 'GET', path: '/routes', label: 'Get All Routes', params: [] },
      { method: 'GET', path: '/routes/{routeId}', label: 'Route Details', params: [
        { name: 'routeId', label: 'Route ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/routes/{routeId}', label: 'Modify Route', params: [
        { name: 'routeId', label: 'Route ID', type: 'number' }
      ], bodyFields: [
        { name: 'fromCity', label: 'From City', type: 'text' },
        { name: 'toCity', label: 'To City', type: 'text' },
        { name: 'breakPoints', label: 'Break Points', type: 'number' },
        { name: 'duration', label: 'Duration (Hrs)', type: 'number' }
      ]},
      { method: 'DELETE', path: '/routes/{routeId}', label: 'Disable Route', params: [
        { name: 'routeId', label: 'Route ID', type: 'number' }
      ]}
    ]
  },

  trips: {
    moduleName: 'Trip', owner: 'Priyadharshini', isMine: false,
    description: 'Core operational scheduling and trip search engine.',
    endpoints: [
      { method: 'POST', path: '/trips', label: 'Create Trip', params: [], bodyFields: [
        { name: 'busId', label: 'Bus ID', type: 'number' },
        { name: 'routeId', label: 'Route ID', type: 'number' },
        { name: 'driver1Id', label: 'Driver 1 ID', type: 'number' },
        { name: 'boardingAddressId', label: 'Boarding ID', type: 'number' },
        { name: 'droppingAddressId', label: 'Dropping ID', type: 'number' },
        { name: 'departureTime', label: 'Departure', type: 'text' },
        { name: 'arrivalTime', label: 'Arrival', type: 'text' },
        { name: 'tripDate', label: 'Trip Date', type: 'text' },
        { name: 'fare', label: 'Fare', type: 'number' }
      ]},
      { method: 'GET', path: '/trips', label: 'List All Trips', params: [] },
      { method: 'GET', path: '/trips/{tripId}', label: 'Trip Details', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]},
      { method: 'GET', path: '/trips/search', label: 'Search Trips', params: [], queryParams: [
        { name: 'fromCity', label: 'From City', type: 'text' },
        { name: 'toCity', label: 'To City', type: 'text' },
        { name: 'date', label: 'Date', type: 'text' }
      ]},
      { method: 'PUT', path: '/trips/{tripId}', label: 'Update Trip', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ], bodyFields: [
        { name: 'busId', label: 'Bus ID', type: 'number' },
        { name: 'routeId', label: 'Route ID', type: 'number' },
        { name: 'driver1Id', label: 'Driver 1 ID', type: 'number' },
        { name: 'boardingAddressId', label: 'Boarding ID', type: 'number' },
        { name: 'droppingAddressId', label: 'Dropping ID', type: 'number' },
        { name: 'departureTime', label: 'Departure', type: 'text' },
        { name: 'arrivalTime', label: 'Arrival', type: 'text' },
        { name: 'tripDate', label: 'Trip Date', type: 'text' },
        { name: 'fare', label: 'Fare', type: 'number' }
      ]},
      { method: 'PATCH', path: '/trips/{tripId}/close', label: 'Close Trip', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]}
    ]
  },

  customers: {
    moduleName: 'Customer', owner: 'Ajitha', isMine: false,
    description: 'Customer lifecycle and profile registry.',
    endpoints: [
{ method: 'POST', path: '/customers', label: 'Register Customer', params: [], bodyFields: [
        { name: 'name', label: 'Name', type: 'text' },
        { name: 'email', label: 'Email', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' },
        { name: 'addressId', label: 'Address ID', type: 'number' }
      ]},
      { method: 'GET', path: '/customers/{customerId}', label: 'Customer Profile', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ]},
      { method: 'PUT', path: '/customers/{customerId}', label: 'Update Customer', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ], bodyFields: [
        { name: 'name', label: 'Name', type: 'text' },
        { name: 'email', label: 'Email', type: 'text' },
        { name: 'phone', label: 'Phone', type: 'text' },
        { name: 'addressId', label: 'Address ID', type: 'number' }
      ]},
      { method: 'GET', path: '/customers', label: 'Customer List (Admin)', params: [] }
    ]
  },

  bookings: {
    moduleName: 'Booking', owner: 'Vignesh', isMine: true,
    description: 'Transactional flow for seat reservation and inventory.',
    endpoints: [
      { method: 'POST', path: '/trips/{tripId}/bookings', label: 'Book Seat', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ], bodyFields: [
        { name: 'customerId', label: 'Customer ID', type: 'number' },
        { name: 'seatNumber', label: 'Seat #', type: 'number' }
      ]},
      { method: 'GET', path: '/customers/{customerId}/bookings', label: 'Customer Bookings', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ]},
      { method: 'GET', path: '/bookings/{bookingId}', label: 'Booking Details', params: [
        { name: 'bookingId', label: 'Booking ID', type: 'number' }
      ]},
      { method: 'PATCH', path: '/bookings/{bookingId}/cancel', label: 'Cancel Booking', params: [
        { name: 'bookingId', label: 'Booking ID', type: 'number' }
      ]},
      { method: 'GET', path: '/trips/{tripId}/seats/booked', label: 'Booked Seats', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]},
      { method: 'GET', path: '/trips/{tripId}/seats/available', label: 'Available Seats', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]}
    ]
  },

  payments: {
    moduleName: 'Payment', owner: 'Vignesh', isMine: true,
    description: 'Financial transactions and reconciliation history.',
    endpoints: [
{ method: 'POST', path: '/payments', label: 'Make Payment', params: [], bodyFields: [
        { name: 'bookingId', label: 'Booking ID', type: 'number' },
        { name: 'customerId', label: 'Customer ID', type: 'number' },
        { name: 'amount', label: 'Amount', type: 'number' },
        { name: 'paymentStatus', label: 'Payment Status (Success/Failed)', type: 'text' }
      ]},
      { method: 'GET', path: '/payments/{paymentId}', label: 'Payment Details', params: [
        { name: 'paymentId', label: 'Payment ID', type: 'number' }
      ]},
      { method: 'GET', path: '/payments/customers/{customerId}/payments', label: 'Payment History', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ]},
      { method: 'GET', path: '/payments/bookings/{bookingId}/payment', label: 'Booking Payment Info', params: [
        { name: 'bookingId', label: 'Booking ID', type: 'number' }
      ]},
      { method: 'PATCH', path: '/payments/{paymentId}/status', label: 'Update Payment Status', params: [
        { name: 'paymentId', label: 'Payment ID', type: 'number' }
      ], queryParams: [
        { name: 'status', label: 'New Status (Success/Failed)', type: 'text' }
      ]}
    ]
  },

  reviews: {
    moduleName: 'Review', owner: 'Priyadharshini', isMine: false,
    description: 'Feedback loop for service quality and trip evaluation.',
    endpoints: [
      { method: 'POST', path: '/trips/{tripId}/reviews', label: 'Submit Review', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ], bodyFields: [
        { name: 'customerId', label: 'Customer ID', type: 'number' },
        { name: 'rating', label: 'Rating (1-5)', type: 'number' },
        { name: 'comment', label: 'Comment', type: 'text' }
      ]},
      { method: 'GET', path: '/trips/{tripId}/reviews', label: 'Trip Reviews', params: [
        { name: 'tripId', label: 'Trip ID', type: 'number' }
      ]},
      { method: 'GET', path: '/customers/{customerId}/reviews', label: 'Customer Reviews', params: [
        { name: 'customerId', label: 'Customer ID', type: 'number' }
      ]},
      { method: 'DELETE', path: '/reviews/{reviewId}', label: 'Remove Review', params: [
        { name: 'reviewId', label: 'Review ID', type: 'number' }
      ]}
    ]
  }
};
