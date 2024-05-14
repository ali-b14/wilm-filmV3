import './login.scss';

import React, { useEffect } from 'react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { logout } from 'app/shared/reducers/authentication';

export const Logout = () => {
  const logoutUrl = useAppSelector(state => state.authentication.logoutUrl);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(logout());

    const timeoutId = setTimeout(() => {
      if (logoutUrl) {
        window.location.href = logoutUrl;
      } else {
        window.location.href = '/';
      }
    }, 3000);

    return () => clearTimeout(timeoutId);
  }, [dispatch, logoutUrl]);

  return (
    <div className="logout-back">
      <div className="logout-content">
        <h4>Logged out successfully!</h4>
      </div>
    </div>
  );
};

export default Logout;
