import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/video">
        <span className="dropdown-options">
        <Translate contentKey="global.menu.entities.video" />
        </span>
      </MenuItem>
      <MenuItem icon="asterisk" to="/video-meta-data">
        <span className="dropdown-options">
          <Translate contentKey="global.menu.entities.videoMetaData" />
        </span>
      </MenuItem>
      <MenuItem icon="asterisk" to="/comment">
        <span className="dropdown-options">
          <Translate contentKey="global.menu.entities.comment" />
        </span>
      </MenuItem>
      <MenuItem icon="asterisk" to="/like">
        <span className="dropdown-options">
          <Translate contentKey="global.menu.entities.like" />
        </span>
      </MenuItem>
      <MenuItem icon="asterisk" to="/watched">
        <span className="dropdown-options">
          <Translate contentKey="global.menu.entities.watched" />
        </span>
      </MenuItem>
      <MenuItem icon="asterisk" to="/watch-later">
        <span className="dropdown-options">
          <Translate contentKey="global.menu.entities.watchLater" />
        </span>
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
