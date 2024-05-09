import './account.scss';

import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

const accountMenuItemsAuthenticated = () => (
  <span className="dropdown-background">
    <MenuItem icon="wrench" to="/account/settings" data-cy="settings">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.account.settings">Settings</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="lock" to="/account/password" data-cy="passwordItem">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.account.password">Password</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="sign-out-alt" to="/logout" data-cy="logout">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.account.logout">Sign out</Translate>
      </span>
    </MenuItem>
  </span>
);

const accountMenuItems = () => (
  <>
    <MenuItem id="login-item" icon="sign-in-alt" to="/login" data-cy="login">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.account.login">Sign in</Translate>
      </span>
    </MenuItem>
    <MenuItem icon="user-plus" to="/account/register" data-cy="register">
      <span className="dropdown-options">
        <Translate contentKey="global.menu.account.register">Register</Translate>
      </span>
    </MenuItem>
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <NavDropdown icon="user" name={translate('global.menu.account.main')} id="account-menu" data-cy="accountMenu">
    {isAuthenticated && accountMenuItemsAuthenticated()}
    {!isAuthenticated && accountMenuItems()}
  </NavDropdown>
);

export default AccountMenu;
