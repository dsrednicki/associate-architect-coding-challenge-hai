import * as React from "react";

declare module "react" {
  namespace JSX {
    interface IntrinsicElements {
      "ui5-list": any;
      "ui5-list-item": any;
      "ui5-title": any;
      "ui5-label": any;
      "ui5-icon": any;
      "ui5-card": any;
      "ui5-shellbar": any;
      "ui5-card-header": any;
      "ui5-icon": any;
      "ui5-timeline": any;
      "ui5-timeline-item": any;
      "ui5-avatar": any;
      "ui5-shellbar-spacer": any;
      "ui5-popover": any;
      "ui5-button": any;
      "ui5-input": any;

    }
  }
}