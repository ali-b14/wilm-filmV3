import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './watched.reducer';

export const WatchedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const watchedEntity = useAppSelector(state => state.watched.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="watchedDetailsHeading">
          <Translate contentKey="wilmFilmApp.watched.detail.title">Watched</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{watchedEntity.id}</dd>
          <dt>
            <span id="watchedAt">
              <Translate contentKey="wilmFilmApp.watched.watchedAt">Watched At</Translate>
            </span>
          </dt>
          <dd>{watchedEntity.watchedAt ? <TextFormat value={watchedEntity.watchedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="wilmFilmApp.watched.video">Video</Translate>
          </dt>
          <dd>{watchedEntity.video ? watchedEntity.video.id : ''}</dd>
          <dt>
            <Translate contentKey="wilmFilmApp.watched.userProfile">User Profile</Translate>
          </dt>
          <dd>{watchedEntity.userProfile ? watchedEntity.userProfile.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/watched" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/watched/${watchedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WatchedDetail;
