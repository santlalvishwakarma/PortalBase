import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowseproductComponent } from './retail/browseproduct/browseproduct.component';
import { TemplateComponent } from './retail/template/template.component';
import { HeaderComponent } from './retail/header/header.component';
import { FooterComponent } from './retail/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    BrowseproductComponent,
    TemplateComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
