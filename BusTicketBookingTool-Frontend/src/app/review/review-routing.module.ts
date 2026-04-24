import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReviewFormComponent } from './components/review-form/review-form.component';
import { ReviewListComponent } from './components/review-list/review-list.component';

const routes: Routes = [
  { path: 'form', component: ReviewFormComponent },
  { path: 'list', component: ReviewListComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
/*
 * - This routing module decides which URL opens each component in this feature.
 * - It keeps navigation rules grouped with the feature so app-routing stays clean.
 * - Angular uses these routes when the browser path changes or when router.navigate is called.
 */
export class ReviewRoutingModule {}
