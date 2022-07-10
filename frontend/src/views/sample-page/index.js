import * as React from 'react';
import TextField from '@mui/material/TextField';
import './styles.css';

// material-ui
import { Typography } from '@mui/material';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';

// project imports
import MainCard from 'ui-component/cards/MainCard';

// ==============================|| SAMPLE PAGE ||============================== //

const SamplePage = () => {
    const [value, setValue] = React.useState(new Date());
    const handleChange = (newValue) => {
        setValue(newValue);
    };

    return (
        <MainCard title="Nueva Reserva">
            <DateTimePicker
                renderInput={(props) => <TextField {...props} />}
                label="DateTimePicker"
                value={value}
                onChange={(newValue) => {
                    setValue(newValue);
                }}
            />
            <TimePicker label="Inicio" value={value} onChange={handleChange} renderInput={(params) => <TextField {...params} />} />
            <TimePicker label="Fin" value={value} onChange={handleChange} renderInput={(params) => <TextField {...params} />} />
        </MainCard>
    );
};

export default SamplePage;
