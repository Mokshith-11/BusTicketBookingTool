import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouteTripRoutingModule } from './route-trip-routing.module';

@NgModule({
  imports: [
    CommonModule,
    RouteTripRoutingModule
  ]
})
/*
 * Beginner guide:
 * - This Angular module groups this feature's components, routing, and imports together.
 * - It imports common Angular tools, declares feature components when needed, and connects the feature routing module.
 * - This folder structure keeps each module independent and easier for team members to understand.
 */
export class RouteTripModule {}
