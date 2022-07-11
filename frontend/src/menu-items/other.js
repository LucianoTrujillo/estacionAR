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
            id: 'new-reservation',
            title: 'Nueva Reserva',
            type: 'item',
            url: '/new-reservation',
            icon: icons.IconCalendar,
            breadcrumbs: false
        }
    ]
};

export default other;
