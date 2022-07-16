import { useEffect, useState } from 'react';
import * as React from 'react';
// material-ui
import { Grid } from '@mui/material';
import { API } from '../../../API';
import SentimentVeryDissatisfiedIcon from '@mui/icons-material/SentimentVeryDissatisfied';

// project imports
import TotalOrderLineChartCard from './TotalOrderLineChartCard';
import { gridSpacing } from 'store/constant';
import UserContext from 'contexts/UserContext';
import { useNavigate } from 'react-router-dom';

import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
// ==============================|| DEFAULT DASHBOARD ||============================== //

const api = new API();

const bull = (
    <Box component="span" sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}>
        •
    </Box>
);

const card = (navigate) => {
    return (
        <React.Fragment>
            <CardContent sx={{ paddingBottom: 0 }}>
                <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                    Este área está muy vacía
                </Typography>
                <Typography
                    sx={{
                        mr: 1,
                        fontSize: '1rem',
                        fontWeight: 200
                    }}
                >
                    <b>Crea algunas reservas para ver su estado en este panel</b>
                </Typography>
            </CardContent>
            <CardActions>
                <Button
                    onClick={() => {
                        navigate('/new-reservation');
                    }}
                    variant="contained"
                    fullWidth={false}
                    style={{ width: 250, alignSelf: 'end', boxShadow: '0px 0px 0px black' }}
                    size="large"
                >
                    Reservar estacionaminto
                </Button>
            </CardActions>
        </React.Fragment>
    );
};

const Dashboard = () => {
    const [isLoading, setLoading] = useState(true);
    const { currentUser } = React.useContext(UserContext);
    const [reservations, setReservations] = React.useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        if (!currentUser || !currentUser.id) {
            navigate('/login');
        }
        api.get('drivers/' + currentUser.id + '/reservations')
            .then((res) => {
                console.log('todo ok???');
                setReservations(res);
                setLoading(false);
            })
            .catch((err) => {
                navigate('/login');
            });
        setLoading(false);
    }, []);

    const handlePayment = (reservation) => {
        setReservations(
            reservations.map((res) => {
                if (res.id === reservation.id) {
                    res.state = 'PAID';
                }
                return res;
            })
        );
    };

    React.useEffect(() => {
        console.log('updated rersr', reservations);
    }, [reservations]);

    console.log('res', reservations);
    return (
        <Grid container spacing={gridSpacing}>
            {reservations.length == 0 && (
                <Grid item xs={24} display="flex" justifyContent={'center'}>
                    <Box sx={{ width: '80%' }}>
                        <Card variant="outlined">{card(navigate)}</Card>
                    </Box>
                </Grid>
            )}
            {reservations
                ? reservations.map((reservation) => (
                      <Grid key={reservation.id} item xs={24}>
                          <Grid container spacing={gridSpacing}>
                              <Grid item lg={24} md={24} sm={24} xs={24}>
                                  <TotalOrderLineChartCard
                                      isLoading={isLoading}
                                      reservation={reservation}
                                      onPay={() => handlePayment(reservation)}
                                  />
                              </Grid>
                          </Grid>
                      </Grid>
                  ))
                : null}
        </Grid>
    );
};

export default Dashboard;
