import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './video-meta-data.reducer';

export const VideoMetaDataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const videoMetaDataEntity = useAppSelector(state => state.videoMetaData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="videoMetaDataDetailsHeading">
          <Translate contentKey="wilmFilmApp.videoMetaData.detail.title">VideoMetaData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{videoMetaDataEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="wilmFilmApp.videoMetaData.title">Title</Translate>
            </span>
          </dt>
          <dd>{videoMetaDataEntity.title}</dd>
          <dt>
            <span id="genre">
              <Translate contentKey="wilmFilmApp.videoMetaData.genre">Genre</Translate>
            </span>
          </dt>
          <dd>{videoMetaDataEntity.genre}</dd>
          <dt>
            <span id="uploadDate">
              <Translate contentKey="wilmFilmApp.videoMetaData.uploadDate">Upload Date</Translate>
            </span>
          </dt>
          <dd>
            {videoMetaDataEntity.uploadDate ? (
              <TextFormat value={videoMetaDataEntity.uploadDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="description">
              <Translate contentKey="wilmFilmApp.videoMetaData.description">Description</Translate>
            </span>
          </dt>
          <dd>{videoMetaDataEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/video-meta-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/video-meta-data/${videoMetaDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VideoMetaDataDetail;
