import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row className="home-back">
      <div className="home-backing">
        {account?.login ? (
          <div>{/* <Alert color="blue">You are logged in as user &quot;{account.login}&quot;.</Alert> */}</div>
        ) : (
          <div></div>
        )}

        <p></p>
      </div>
    </Row>
  );
};

export default Home;
