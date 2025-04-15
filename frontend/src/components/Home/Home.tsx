import { useEffect, useRef } from 'react';
import './Home.css';
import TaskManager from '../TaskManager/TaskManager';
import "@ui5/webcomponents-fiori/dist/ShellBar.js";
import "@ui5/webcomponents/dist/Avatar.js";
import "@ui5/webcomponents-fiori/dist/ShellBarSpacer.js";
import "@ui5/webcomponents/dist/Popover.js"; 
import "@ui5/webcomponents/dist/Button.js";

const Home: React.FC = () => {
  const shellbarRef = useRef(null);
  const popoverRef = useRef(null);

  useEffect(() => {
    const shellbar = shellbarRef.current;
    const actionPopover = popoverRef.current;

    const handleProfileClick = (event) => {
      actionPopover.opener = event.detail.targetRef;
      actionPopover.open = true;
    };
    shellbar.addEventListener("ui5-profile-click", handleProfileClick);
    return () => {
      shellbar.removeEventListener("ui5-profile-click", handleProfileClick);
    };
  }, []);

  return (
    <div className="App">
      <header className="app-header">
        <ui5-shellbar
          id="shellbar"
          ref={shellbarRef}
          primary-title="Associate Architect Coding Challenge"
          secondary-title="Task Manager"
        >
          <img
            slot="logo"
            src="https://sap.github.io/ui5-webcomponents/images/sap-logo-svg.svg"
            alt="SAP Logo"
          />
          <ui5-shellbar-spacer slot="content"></ui5-shellbar-spacer>
          <ui5-avatar slot="profile" icon="customer"></ui5-avatar>
        </ui5-shellbar>
        <ui5-popover id="action-popover" ref={popoverRef} placement="Bottom">
          <div className="action-popover-header">
            <ui5-title>Your Name</ui5-title>
          </div>
          <div className="action-popover-content">
          <ui5-button icon="log" slot="startButton">Logout</ui5-button>
          </div>
        </ui5-popover>
      </header>
      <main className="app-main">
        <TaskManager />
      </main>
    </div>
  );
};

export default Home;