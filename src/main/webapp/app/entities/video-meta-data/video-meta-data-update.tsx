import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVideoMetaData } from 'app/shared/model/video-meta-data.model';
import { getEntity, updateEntity, createEntity, reset } from './video-meta-data.reducer';

export const VideoMetaDataUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const videoMetaDataEntity = useAppSelector(state => state.videoMetaData.entity);
  const loading = useAppSelector(state => state.videoMetaData.loading);
  const updating = useAppSelector(state => state.videoMetaData.updating);
  const updateSuccess = useAppSelector(state => state.videoMetaData.updateSuccess);

  const handleClose = () => {
    navigate('/video-meta-data');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.uploadDate = convertDateTimeToServer(values.uploadDate);

    const entity = {
      ...videoMetaDataEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          uploadDate: displayDefaultDateTime(),
        }
      : {
          ...videoMetaDataEntity,
          uploadDate: convertDateTimeFromServer(videoMetaDataEntity.uploadDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="wilmFilmApp.videoMetaData.home.createOrEditLabel" data-cy="VideoMetaDataCreateUpdateHeading">
            <Translate contentKey="wilmFilmApp.videoMetaData.home.createOrEditLabel">Create or edit a VideoMetaData</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="video-meta-data-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('wilmFilmApp.videoMetaData.title')}
                id="video-meta-data-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('wilmFilmApp.videoMetaData.genre')}
                id="video-meta-data-genre"
                name="genre"
                data-cy="genre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('wilmFilmApp.videoMetaData.uploadDate')}
                id="video-meta-data-uploadDate"
                name="uploadDate"
                data-cy="uploadDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('wilmFilmApp.videoMetaData.description')}
                id="video-meta-data-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/video-meta-data" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default VideoMetaDataUpdate;
