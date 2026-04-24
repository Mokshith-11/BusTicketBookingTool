import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddressComponent } from './components/address/address.component';
import { AgencyComponent } from './components/agency/agency.component';
import { OfficeComponent } from './components/office/office.component';

const routes: Routes = [
  { path: 'agency', component: AgencyComponent },
  { path: 'office', component: OfficeComponent },
  { path: 'address', component: AddressComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AgencyRoutingModule {}
