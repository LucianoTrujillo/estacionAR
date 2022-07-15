import { useEffect, useState } from 'react';
import * as React from 'react';
// material-ui
import { Grid } from '@mui/material';
import { API } from '../../../API';
// project imports
import EarningCard from './EarningCard';
import PopularCard from './PopularCard';
import TotalOrderLineChartCard from './TotalOrderLineChartCard';
import TotalIncomeDarkCard from './TotalIncomeDarkCard';
import TotalIncomeLightCard from './TotalIncomeLightCard';
import TotalGrowthBarChart from './TotalGrowthBarChart';
import { gridSpacing } from 'store/constant';
import UserContext from 'contexts/UserContext';
import { useNavigate } from 'react-router-dom';
// ==============================|| DEFAULT DASHBOARD ||============================== //

const api = new API();

const Dashboard = () => {
    const [isLoading, setLoading] = useState(true);
    const { currentUser } = React.useContext(UserContext);
    const [reservations, setReservations] = React.useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        if (!currentUser.id) {
            navigate('/pages/login');
        }
        api.get('drivers/' + currentUser.id + '/reservations')
            .then((res) => {
                setReservations(res);
                setLoading(false);
            })
            .catch((err) => {
                console.log(err);
                setLoading(false);
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

    return (
        <Grid container spacing={gridSpacing}>
            {reservations.map((reservation) => (
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
            ))}
        </Grid>
    );
};

export default Dashboard;
