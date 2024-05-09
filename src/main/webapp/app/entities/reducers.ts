import video from 'app/entities/video/video.reducer';
import videoMetaData from 'app/entities/video-meta-data/video-meta-data.reducer';
import comment from 'app/entities/comment/comment.reducer';
import like from 'app/entities/like/like.reducer';
import watched from 'app/entities/watched/watched.reducer';
import watchLater from 'app/entities/watch-later/watch-later.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  video,
  videoMetaData,
  comment,
  like,
  watched,
  watchLater,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
