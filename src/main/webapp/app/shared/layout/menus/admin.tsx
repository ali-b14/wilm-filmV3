import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavDropdown } from './menu-components';
import { Translate, translate } from 'react-jhipster';

const adminMenuItems = () => (
  <>
    <MenuItem icon="users" to="/admin/user-management">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.admin.userManagement">User management</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="tachometer-alt" to="/admin/metrics">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.admin.metrics">Metrics</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="heart" to="/admin/health">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.admin.health">Health</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="cogs" to="/admin/configuration">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.admin.configuration">Configuration</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="tasks" to="/admin/logs">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.admin.logs">Logs</Translate>
      </span>
    </MenuItem>
    {/* jhipster-needle-add-element-to-admin-menu - JHipster will add entities to the admin menu here */}
  </>
);

const openAPIItem = () => (
  <MenuItem icon="book" to="/admin/docs">
    <span className="dropdown-options">
      <Translate contentKey="global.menu.admin.apidocs">API</Translate>
    </span>
  </MenuItem>
);

const databaseItem = () => (
  <DropdownItem tag="a" href="./h2-console/" target="_tab">
    <FontAwesomeIcon icon="database" fixedWidth />
    <span className="dropdown-options">
      <Translate contentKey="global.menu.admin.database">Database</Translate>
    </span>
  </DropdownItem>
);

export const AdminMenu = ({ showOpenAPI, showDatabase }) => (
  <NavDropdown icon="users-cog" name={translate('global.menu.admin.main')} id="admin-menu" data-cy="adminMenu">
    {adminMenuItems()}
    {showOpenAPI && openAPIItem()}

    {showDatabase && databaseItem()}
  </NavDropdown>
);

export default AdminMenu;
