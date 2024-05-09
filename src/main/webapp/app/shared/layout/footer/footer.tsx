import './footer.scss';

import React, { useState } from 'react';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <a href="#">
      <img src="/content/images/icons8-linkedin-48.png" alt="LinkedIn Logo" />
    </a>
    <a href="#">
      <img src="/content/images/icons8-github-100.png" alt="Github Logo" style={{ width: '50px' }} />
    </a>
  </div>
);

export interface IFooterProps {
  isAuthenticated: boolean;
}

const Footer = ({ isAuthenticated }: IFooterProps) => {
  return (
    <div className="footer">
      <div className="link-group">
        <div className="link-container">
          <div></div>
          <a href="#">
            <line>About Us</line>
          </a>
          <a href="#">
            <line>Have Questions?</line>
          </a>
          <a href="/Dashboard">
            <line>Watch Now</line>
          </a>
        </div>
        {!isAuthenticated && (
          <div className="link-container">
            <a href="#/account">
              <line>Account</line>
            </a>
            <a href="/account/register">
              <line>Register</line>
            </a>
            <a href="/login">
              <line>Login</line>
            </a>
          </div>
        )}
      </div>
      <div>
        <BrandIcon />
      </div>
    </div>
  );
};

export default Footer;
