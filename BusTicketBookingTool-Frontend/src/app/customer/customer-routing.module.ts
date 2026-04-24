import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerListComponent } from './components/customer-list/customer-list.component';
import { CustomerProfileComponent } from './components/customer-profile/customer-profile.component';

const routes: Routes = [
  { path: 'list', component: CustomerListComponent },
  { path: 'profile', component: CustomerProfileComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule {}
