import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IVideo } from 'app/shared/model/video.model';

export interface ILike {
  id?: number;
  likedAt?: dayjs.Dayjs;
  user?: IUser | null;
  video?: IVideo | null;
}

export const defaultValue: Readonly<ILike> = {};
