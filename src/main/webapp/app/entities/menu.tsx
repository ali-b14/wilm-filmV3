import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/video">
        <Translate contentKey="global.menu.entities.video" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/video-meta-data">
        <Translate contentKey="global.menu.entities.videoMetaData" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/comment">
        <Translate contentKey="global.menu.entities.comment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/like">
        <Translate contentKey="global.menu.entities.like" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/watched">
        <Translate contentKey="global.menu.entities.watched" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/watch-later">
        <Translate contentKey="global.menu.entities.watchLater" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
