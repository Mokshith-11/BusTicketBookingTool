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
/*
 * Beginner guide:
 * - This routing module decides which URL opens each component in this feature.
 * - It keeps navigation rules grouped with the feature so app-routing stays clean.
 * - Angular uses these routes when the browser path changes or when router.navigate is called.
 */
export class CustomerRoutingModule {}
