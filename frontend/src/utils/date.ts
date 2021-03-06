import { format } from 'date-fns';

export const formatDate = (date: Date): string => format(new Date(date), 'MM/dd/yyyy');
