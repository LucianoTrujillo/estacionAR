import * as React from 'react';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { API } from '../../../../API';
// material-ui
import { useTheme } from '@mui/material/styles';
import {
    Box,
    Button,
    Checkbox,
    Divider,
    FormControl,
    FormControlLabel,
    FormHelperText,
    Grid,
    IconButton,
    InputAdornment,
    InputLabel,
    OutlinedInput,
    Stack,
    Typography,
    useMediaQuery
} from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';

// third party
import * as Yup from 'yup';
import { Formik } from 'formik';
import UserContext from '../../../../contexts/UserContext';

// project imports
import useScriptRef from 'hooks/useScriptRef';
import AnimateButton from 'ui-component/extended/AnimateButton';

// assets
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

import Google from 'assets/images/icons/social-google.svg';
import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';
// ============================|| FIREBASE - LOGIN ||============================ //

const FirebaseLogin = ({ ...others }) => {
    const theme = useTheme();
    const scriptedRef = useScriptRef();
    const matchDownSM = useMediaQuery(theme.breakpoints.down('md'));
    const customization = useSelector((state) => state.customization);
    const [checked, setChecked] = useState(true);
    const { currentUser, setCurrentUser } = React.useContext(UserContext);
    const navigate = useNavigate();
    const api = new API();

    const googleHandler = async () => {
        console.error('Login');
    };

    const [showlicensePlate, setShowlicensePlate] = useState(false);
    const handleClickShowlicensePlate = () => {
        setShowlicensePlate(!showlicensePlate);
    };

    const handleMouseDownlicensePlate = (event) => {
        event.preventDefault();
    };

    const [open, setOpen] = React.useState(false);
    const handleClose = () => {
        setOpen(false);
    };
    const handleToggle = () => {
        setOpen(!open);
    };

    return (
        <>
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={open} onClick={handleClose}>
                <CircularProgress color="inherit" />
            </Backdrop>
            <Grid container direction="column" justifyContent="center" spacing={2}>
                <Grid item xs={12}>
                    <AnimateButton>
                        <Button
                            disableElevation
                            fullWidth
                            onClick={googleHandler}
                            size="large"
                            variant="outlined"
                            sx={{
                                color: 'grey.700',
                                backgroundColor: theme.palette.grey[50],
                                borderColor: theme.palette.grey[100]
                            }}
                        >
                            <Box sx={{ mr: { xs: 1, sm: 2, width: 20 } }}>
                                <img src={Google} alt="google" width={16} height={16} style={{ marginRight: matchDownSM ? 8 : 16 }} />
                            </Box>
                            Sign in with Google
                        </Button>
                    </AnimateButton>
                </Grid>
                <Grid item xs={12}>
                    <Box
                        sx={{
                            alignItems: 'center',
                            display: 'flex'
                        }}
                    >
                        <Divider sx={{ flexGrow: 1 }} orientation="horizontal" />

                        <Button
                            variant="outlined"
                            sx={{
                                cursor: 'unset',
                                m: 2,
                                py: 0.5,
                                px: 7,
                                borderColor: `${theme.palette.grey[100]} !important`,
                                color: `${theme.palette.grey[900]}!important`,
                                fontWeight: 500,
                                borderRadius: `${customization.borderRadius}px`
                            }}
                            disableRipple
                            disabled
                        >
                            OR
                        </Button>

                        <Divider sx={{ flexGrow: 1 }} orientation="horizontal" />
                    </Box>
                </Grid>
                <Grid item xs={12} container alignItems="center" justifyContent="center">
                    <Box sx={{ mb: 2 }}>
                        <Typography variant="subtitle1">Inicia sesión con DNI y matrícula</Typography>
                    </Box>
                </Grid>
            </Grid>

            <Formik
                initialValues={{
                    dni: '',
                    licensePlate: '123456',
                    submit: null
                }}
                validationSchema={Yup.object().shape({
                    dni: Yup.string().max(255).required('dni is required'),
                    licensePlate: Yup.string().max(255).required('licensePlate is required')
                })}
                onSubmit={async (values, { setErrors, setStatus, setSubmitting }) => {
                    try {
                        if (scriptedRef.current) {
                            setStatus({ success: true });
                            setSubmitting(false);
                        }
                        handleToggle();

                        setTimeout(() => {
                            api.get('drivers', values)
                                .then((json) => {
                                    let driver = json.find((driv) => driv.dni === values.dni);

                                    if (!driver || driver.licensePlate !== values.licensePlate) {
                                        setStatus({ success: false });
                                        setErrors({ submit: 'No existe el usuario con esos datos' });
                                        setSubmitting(false);
                                    } else {
                                        setCurrentUser(driver);
                                        navigate('/');
                                    }
                                })
                                .catch((error) => {
                                    setStatus({ success: false });
                                    setErrors({ submit: 'Hubo un error al tratar de inciar sesión' });
                                    setSubmitting(false);
                                });

                            handleClose();
                        }, 1000);
                    } catch (err) {
                        console.error(err);
                        if (scriptedRef.current) {
                            setStatus({ success: false });
                            setErrors({ submit: err.message });
                            setSubmitting(false);
                        }
                    }
                }}
            >
                {({ errors, handleBlur, handleChange, handleSubmit, isSubmitting, touched, values }) => (
                    <form noValidate onSubmit={handleSubmit} {...others}>
                        <FormControl fullWidth error={Boolean(touched.dni && errors.dni)} sx={{ ...theme.typography.customInput }}>
                            <InputLabel htmlFor="outlined-adornment-dni-login">dni</InputLabel>
                            <OutlinedInput
                                id="outlined-adornment-dni-login"
                                type="dni"
                                value={values.dni}
                                name="dni"
                                onBlur={handleBlur}
                                onChange={handleChange}
                                label="dni Address / Username"
                                inputProps={{}}
                            />
                            {touched.dni && errors.dni && (
                                <FormHelperText error id="standard-weight-helper-text-dni-login">
                                    {errors.dni}
                                </FormHelperText>
                            )}
                        </FormControl>

                        <FormControl
                            fullWidth
                            error={Boolean(touched.licensePlate && errors.licensePlate)}
                            sx={{ ...theme.typography.customInput }}
                        >
                            <InputLabel htmlFor="outlined-adornment-licensePlate-login">Matricula</InputLabel>
                            <OutlinedInput
                                id="outlined-adornment-licensePlate-login"
                                type={showlicensePlate ? 'text' : 'licensePlate'}
                                value={values.licensePlate}
                                name="licensePlate"
                                onBlur={handleBlur}
                                onChange={handleChange}
                                endAdornment={
                                    <InputAdornment position="end">
                                        <IconButton
                                            aria-label="toggle licensePlate visibility"
                                            onClick={handleClickShowlicensePlate}
                                            onMouseDown={handleMouseDownlicensePlate}
                                            edge="end"
                                            size="large"
                                        >
                                            {showlicensePlate ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                                }
                                label="licensePlate"
                                inputProps={{}}
                            />
                            {touched.licensePlate && errors.licensePlate && (
                                <FormHelperText error id="standard-weight-helper-text-licensePlate-login">
                                    {errors.licensePlate}
                                </FormHelperText>
                            )}
                        </FormControl>
                        <Stack direction="row" alignItems="center" justifyContent="space-between" spacing={1}>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        checked={checked}
                                        onChange={(event) => setChecked(event.target.checked)}
                                        name="checked"
                                        color="primary"
                                    />
                                }
                                label="Remember me"
                            />
                            <Typography variant="subtitle1" color="secondary" sx={{ textDecoration: 'none', cursor: 'pointer' }}>
                                Forgot licensePlate?
                            </Typography>
                        </Stack>
                        {errors.submit && (
                            <Box sx={{ mt: 3 }}>
                                <FormHelperText error>{errors.submit}</FormHelperText>
                            </Box>
                        )}

                        <Box sx={{ mt: 2 }}>
                            <AnimateButton>
                                <Button
                                    disableElevation
                                    disabled={isSubmitting}
                                    fullWidth
                                    size="large"
                                    type="submit"
                                    variant="contained"
                                    color="secondary"
                                >
                                    Sign in
                                </Button>
                            </AnimateButton>
                        </Box>
                    </form>
                )}
            </Formik>
        </>
    );
};

export default FirebaseLogin;
