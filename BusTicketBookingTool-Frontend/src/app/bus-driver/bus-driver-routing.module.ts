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
export class BusDriverRoutingModule {}
