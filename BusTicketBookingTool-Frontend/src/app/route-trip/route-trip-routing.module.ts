import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouteComponent } from './components/route/route.component';
import { TripComponent } from './components/trip/trip.component';
import { TripSearchComponent } from './components/trip-search/trip-search.component';

const routes: Routes = [
  { path: 'route', component: RouteComponent },
  { path: 'trip', component: TripComponent },
  { path: 'trip-search', component: TripSearchComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RouteTripRoutingModule {}
