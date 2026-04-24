import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BusComponent } from './components/bus/bus.component';
import { DriverComponent } from './components/driver/driver.component';

const routes: Routes = [
  { path: 'bus', component: BusComponent },
  { path: 'driver', component: DriverComponent }
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
export class BusDriverRoutingModule {}
