import './header.scss';

import React, { useState } from 'react';

import { Navbar, Nav, NavbarToggler, Collapse } from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';

import { Home, Brand } from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu } from '../menus';
import { Link } from 'react-router-dom';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
}

const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);

  // const renderDevRibbon = () =>
  //   props.isInProduction === false ? (
  //     <div className="ribbon dev">
  //       <a href="">Development</a>
  //     </div>
  //   ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);

  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      <LoadingBar className="loading-bar" />
      <Navbar data-cy="navbar" dark expand="md" fixed="top" className="navbar-color">
        <NavbarToggler aria-label="Menu" onClick={toggleMenu} />
        <Brand />
        <Collapse isOpen={menuOpen} navbar>
          <Nav id="header-tabs" className="ms-auto" navbar>
            <Home />
            {props.isAuthenticated && props.isAdmin && <EntitiesMenu />}
            {props.isAuthenticated && props.isAdmin && (
              <AdminMenu showOpenAPI={props.isOpenAPIEnabled} showDatabase={!props.isInProduction} />
            )}
            <AccountMenu isAuthenticated={props.isAuthenticated} />
            {props.isAuthenticated && (
              <Link to="/Dashboard" className="nav-link">
                <button className="button-design">Movies</button>
              </Link>
            )}
            {props.isAuthenticated && (
              <Link to="/Movie" className="nav-link">
                <button className="button-design">Temp Movie</button>
              </Link>
            )}
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
};

export default Header;
