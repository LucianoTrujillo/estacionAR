import * as React from 'react';
import './styles.css';
import TextField from '@mui/material/TextField';

// material-ui
import { Typography, Divider, List, ListItem, Box } from '@mui/material';
import Button from '@mui/material/Button';
import { API } from '../../API';
// project imports
import MainCard from 'ui-component/cards/MainCard';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import UserContext from 'contexts/UserContext';
import { useNavigate } from 'react-router-dom';

const api = new API();

// ==============================|| INSPECTOR PAGE ||============================== //

const SamplePage = () => {
    const [inspectorName, setInspectorName] = React.useState('');
    const [licensePlate, setStreetNumber] = React.useState('');
    const [alertOpen, setAlertOpen] = React.useState(false);
    const [alertMsg, setAlertMsg] = React.useState('');
    const [severity, setSeverity] = React.useState('success');

    const handleInspection = async () => {
        api.get(`inspect`, {
            license_plate: licensePlate,
            inspector: inspectorName,
            location: {
                streetName: street,
                streetNumber: Number.parseInt(streetNumber, 10)
            }
        })
            .then((res) => {
                console.log(res);
                setAlertMsg('El conductor tiene una reserva válida');
                setSeverity('success');
                setAlertOpen(true);
            })
            .catch((err) => {
                console.log('abriendo msg de error');
                setAlertMsg(err.toString());
                setSeverity('error');
                setAlertOpen(true);
            });
    };

    return (
        <MainCard title="Nueva Reserva">
            <Box style={{ display: 'flex', flexDirection: 'column' }}>
                <List row={'true'} style={{ display: 'flex' }}>
                    <ListItem style={{ width: 300 }}>
                        <List>
                            <Typography variant="body1" marginBottom={2}>
                                Elegí cuando comienza la reserva
                            </Typography>
                            <DateTimePicker
                                label="Fecha y hora de inicio"
                                value={startDateTime}
                                onChange={(val) => setStartDateTime(val)}
                                renderInput={(params) => <TextField {...params} />}
                            />
                            <Typography variant="body1" marginTop={3} marginBottom={2}>
                                Elegí cuando termina la reserva
                            </Typography>
                            <DateTimePicker
                                label="Fecha y hora de fin"
                                value={endDateTime}
                                onChange={(val) => setEndDateTime(val)}
                                renderInput={(params) => <TextField {...params} />}
                            />
                        </List>
                    </ListItem>
                    <ListItem style={{ width: 300, display: 'flex', flexDirection: 'column' }}>
                        <List>
                            <Typography variant="body1" marginBottom={2}>
                                Elegí la calle
                            </Typography>
                            <TextField
                                id="outlined-basic"
                                label="Calle"
                                variant="outlined"
                                value={street}
                                onChange={(e) => setStreet(e.target.value)}
                            />
                            <Typography variant="body1" marginBottom={2} marginTop={3}>
                                Elegí la altura
                            </Typography>
                            <TextField
                                id="outlined-basic"
                                label="Altura"
                                variant="outlined"
                                value={streetNumber}
                                // leaves only the numbers in the input
                                onChange={(e) => setStreetNumber(e.target.value.replace(/[^0-9]/g, ''))}
                            />
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
            <Snackbar
                anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                open={alertOpen}
                message={'I love snacks'}
                key={'bottom left'}
                autoHideDuration={10000}
                onClose={() => setAlertOpen(false)}
            >
                <Alert elevation={6} variant="filled" severity={severity}>
                    {alertMsg}
                    <Button color="inherit" size="small" onClick={() => setAlertOpen(false)}>
                        Ok
                    </Button>
                </Alert>
            </Snackbar>
        </MainCard>
    );
};

export default SamplePage;
