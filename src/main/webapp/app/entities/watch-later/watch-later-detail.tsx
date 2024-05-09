import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './watch-later.reducer';

export const WatchLaterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const watchLaterEntity = useAppSelector(state => state.watchLater.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="watchLaterDetailsHeading">
          <Translate contentKey="wilmFilmApp.watchLater.detail.title">WatchLater</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{watchLaterEntity.id}</dd>
          <dt>
            <Translate contentKey="wilmFilmApp.watchLater.video">Video</Translate>
          </dt>
          <dd>{watchLaterEntity.video ? watchLaterEntity.video.id : ''}</dd>
          <dt>
            <Translate contentKey="wilmFilmApp.watchLater.userProfile">User Profile</Translate>
          </dt>
          <dd>{watchLaterEntity.userProfile ? watchLaterEntity.userProfile.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/watch-later" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/watch-later/${watchLaterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WatchLaterDetail;
