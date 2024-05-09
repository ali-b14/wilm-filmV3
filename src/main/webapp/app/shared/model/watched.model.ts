import dayjs from 'dayjs';
import { IVideo } from 'app/shared/model/video.model';
import { IUser } from 'app/shared/model/user.model';

export interface IWatched {
  id?: number;
  watchedAt?: dayjs.Dayjs;
  video?: IVideo | null;
  userProfile?: IUser | null;
}

export const defaultValue: Readonly<IWatched> = {};
