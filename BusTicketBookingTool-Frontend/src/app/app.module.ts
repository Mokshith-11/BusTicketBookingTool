import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { CoreModule } from './core/core.module';
import { apiInterceptor } from './core/interceptors/api.interceptor';
import { SharedModule } from './shared/shared.module';

@NgModule({
  imports: [
    BrowserModule,
    AppComponent,
    AppRoutingModule,
    CoreModule,
    SharedModule
  ],
  providers: [
    provideHttpClient(withInterceptors([apiInterceptor]))
  ],
  bootstrap: [AppComponent]
})
/*
 * Beginner guide:
 * - This Angular module groups this feature's components, routing, and imports together.
 * - It imports common Angular tools, declares feature components when needed, and connects the feature routing module.
 * - This folder structure keeps each module independent and easier for team members to understand.
 */
export class AppModule {}
