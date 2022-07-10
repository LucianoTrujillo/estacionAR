// assets
import { IconHelp, IconCalendar } from '@tabler/icons';

// constant
const icons = { IconHelp, IconCalendar };

// ==============================|| SAMPLE PAGE & DOCUMENTATION MENU ITEMS ||============================== //

const other = {
    id: 'sample-docs-roadmap',
    type: 'group',
    children: [
        {
            id: 'sample-page',
            title: 'Nueva Reserva',
            type: 'item',
            url: '/sample-page',
            icon: icons.IconCalendar,
            breadcrumbs: false
        }
    ]
};

export default other;
