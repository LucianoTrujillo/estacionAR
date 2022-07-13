import * as React from 'react';
import './styles.css';
import TextField from '@mui/material/TextField';

// material-ui
import { Typography, Divider, List, ListItem, Box } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { TimePicker } from '@mui/x-date-pickers/TimePicker';
import Button from '@mui/material/Button';
import { API } from '../../API';
// project imports
import MainCard from 'ui-component/cards/MainCard';

const api = new API();

// ==============================|| SAMPLE PAGE ||============================== //

const SamplePage = () => {
    const [value, setValue] = React.useState(new Date());
    const handleChange = (newValue) => {
        setValue(newValue);
    };

    const handleReserve = async () => {
        console.log('a ver la date', new Date().toString());
        api.post('drivers/1/reservations', {
            startTime: '2007-12-03T10:15:30',
            endTime: '2007-12-03T10:15:30',
            location: {
                streetName: '',
                streetNumber: 122
            }
        })
            .then((res) => {
                console.log(res);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    return (
        <MainCard title="Nueva Reserva">
            <Box style={{ display: 'flex', flexDirection: 'column' }}>
                <List row={'true'} style={{ display: 'flex' }}>
                    <ListItem style={{ width: 300 }}>
                        <List>
                            <Typography variant="body1" marginBottom={2}>
                                Elegí el día
                            </Typography>
                            <DatePicker
                                renderInput={(props) => <TextField {...props} />}
                                label="DateTimePicker"
                                value={value}
                                onChange={(newValue) => {
                                    setValue(newValue);
                                }}
                            />
                            <Typography variant="body1" marginBottom={2} marginTop={3}>
                                Elegí la hora de comienzo
                            </Typography>
                            <TimePicker
                                label="Inicio"
                                value={value}
                                onChange={handleChange}
                                renderInput={(params) => <TextField {...params} />}
                            />
                            <Typography variant="body1" marginBottom={2} marginTop={3}>
                                Elegí la hora de fin
                            </Typography>
                            <TimePicker
                                label="Fin"
                                value={value}
                                onChange={handleChange}
                                renderInput={(params) => <TextField {...params} />}
                            />
                        </List>
                    </ListItem>
                    <ListItem style={{ width: 300, display: 'flex', flexDirection: 'column' }}>
                        <List>
                            <Typography variant="body1" marginBottom={2}>
                                Elegí la calle
                            </Typography>
                            <TextField id="outlined-basic" label="Calle" variant="outlined" />
                            <Typography variant="body1" marginBottom={2} marginTop={3}>
                                Elegí la altura
                            </Typography>
                            <TextField id="outlined-basic" label="Calle" variant="outlined" />
                        </List>
                    </ListItem>
                </List>
                {/* Button with text 'Resevar' at the right-most position*/}
                <Button
                    onClick={handleReserve}
                    variant="contained"
                    fullWidth={false}
                    style={{ width: 250, alignSelf: 'end', boxShadow: '0px 0px 0px black' }}
                >
                    Reservar
                </Button>
            </Box>
        </MainCard>
    );
};

export default SamplePage;
