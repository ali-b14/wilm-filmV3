import dayjs from 'dayjs';

export interface IVideoMetaData {
  id?: number;
  title?: string;
  genre?: string;
  uploadDate?: dayjs.Dayjs;
  description?: string | null;
}

export const defaultValue: Readonly<IVideoMetaData> = {};
