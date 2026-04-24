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
/*
 * Beginner guide:
 * - This routing module decides which URL opens each component in this feature.
 * - It keeps navigation rules grouped with the feature so app-routing stays clean.
 * - Angular uses these routes when the browser path changes or when router.navigate is called.
 */
export class RouteTripRoutingModule {}
