import PropTypes from "prop-types";
import { useState } from "react";

// material-ui
import { useTheme, styled } from "@mui/material/styles";
import {
  Avatar,
  Box,
  Button,
  Grid,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Typography,
} from "@mui/material";

// third-party
import Chart from "react-apexcharts";

// project imports
import MainCard from "ui-component/cards/MainCard";
import SkeletonTotalOrderCard from "ui-component/cards/Skeleton/EarningCard";

import RadialData from "./chart-data/total-order-month-line-chart";

// assets
import ConfirmationNumberIcon from "@mui/icons-material/ConfirmationNumber";
import ArrowDownwardIcon from "@mui/icons-material/ArrowDownward";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import PushPinIcon from "@mui/icons-material/PushPin";
import TableChartOutlinedIcon from "@mui/icons-material/TableChartOutlined";
import PaidIcon from "@mui/icons-material/Paid";
import LocationOnIcon from "@mui/icons-material/LocationOn";

import { API } from "../../../API";
import * as React from "react";
import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import UserContext from "../../../contexts/UserContext";
import Modal from "@mui/material/Modal";
import logo from "../../../assets/images/icon.svg";

const api = new API();

const CardWrapper = styled(MainCard)(({ theme, bgColor, color }) => ({
  backgroundColor: bgColor,
  color: color,
  overflow: "hidden",
  position: "relative",
  "&>div": {
    position: "relative",
    zIndex: 5,
  },
  "&:after": {
    content: '""',
    position: "absolute",
    width: 210,
    height: 210,
    background: theme.palette.primary[800],
    borderRadius: "50%",
    zIndex: 1,
    top: -85,
    right: -95,
    [theme.breakpoints.down("sm")]: {
      top: -105,
      right: -140,
    },
  },
  "&:before": {
    content: '""',
    position: "absolute",
    zIndex: 1,
    width: 210,
    height: 210,
    background: theme.palette.primary[800],
    borderRadius: "50%",
    top: -125,
    right: -15,
    opacity: 0.5,
    [theme.breakpoints.down("sm")]: {
      top: -155,
      right: -70,
    },
  },
}));

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

// ==============================|| DASHBOARD - TOTAL ORDER LINE CHART CARD ||============================== //

const TotalOrderLineChartCard = ({ isLoading, reservation, onPay }) => {
  const theme = useTheme();
  console.log(reservation);
  const [timeValue, setTimeValue] = useState(false);
  const [alertOpen, setAlertOpen] = React.useState(false);
  const [alertMsg, setAlertMsg] = React.useState("");
  const [severity, setSeverity] = React.useState("success");
  const { currentUser, setCurrentUser } = React.useContext(UserContext);
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    api
      .get(
        "drivers/" +
        currentUser.id +
        "/reservations/" +
        reservation.id +
        "/receipt"
      )
      .then((res) => {
        setReceipt({
          receiptId: res.id,
        });
        setOpen(true);
      });
  };
  const handleClose = () => setOpen(false);
  const [receipt, setReceipt] = React.useState(null);

  const handlePayment = () => {
    api
      .get(
        "drivers/" + currentUser.id + "/reservations/" + reservation.id + "/pay"
      )
      .then((res) => {
        console.log(res);

        setReceipt(res);

        setAlertMsg("El pago se realiz?? correctamente");
        setSeverity("success");
        setAlertOpen(true);
        onPay();
      })
      .catch((err) => {
        console.log("abriendo msg de error");
        setAlertMsg(err.toString());
        setSeverity("error");
        setAlertOpen(true);
      });
  };
  return (
    <>
      {isLoading ? (
        <SkeletonTotalOrderCard />
      ) : (
        <CardWrapper
          border={true}
          content={false}
          bgColor={
            reservation.state === "PAID"
              ? theme.palette.secondary.light
              : theme.palette.primary.dark
          }
          color={reservation.state === "PAID" ? "#000000" : "#fff"}
        >
          <Box sx={{ p: 2.25 }}>
            <Grid container direction="column">
              <Grid item>
                <Grid container justifyContent="space-between">
                  <Grid item display={"flex"} direction={"row"}>
                    <Avatar
                      style={{ marginRight: 20 }}
                      variant="rounded"
                      sx={{
                        ...theme.typography.commonAvatar,
                        ...theme.typography.largeAvatar,
                        backgroundColor: theme.palette.primary[800],
                        color: "#fff",
                        mt: 2,
                      }}
                    >
                      <LocationOnIcon fontSize="inherit" />
                    </Avatar>
                    {reservation.state === "PAID" ? (
                      <Typography
                        sx={{
                          fontSize: "2.125rem",
                          fontWeight: 800,
                          mr: 2,
                          mt: 1.75,
                          mb: 0.75,
                        }}
                      >
                        {reservation.location.streetName}{" "}
                        {reservation.location.streetNumber}
                      </Typography>
                    ) : (
                      <Typography
                        sx={{
                          fontSize: "2.125rem",
                          fontWeight: 800,
                          mr: 2,
                          mt: 1.75,
                          mb: 0.75,
                        }}
                      >
                        {reservation.location.streetName}{" "}
                        {reservation.location.streetNumber}
                      </Typography>
                    )}
                  </Grid>
                  {reservation.state === "UNPAID" && (
                    <Grid item>
                      <Button
                        variant={!timeValue ? "contained" : "text"}
                        size="small"
                        sx={{ color: theme.palette.action.active }}
                        onClick={(e) => handlePayment()}
                      >
                        Pagar
                      </Button>
                    </Grid>
                  )}
                  {reservation.state === "PAID" && (
                    <Grid item>
                      <div >
                        {receipt && (
                          <Modal

                            open={open}
                            onClose={handleClose}
                            aria-labelledby="modal-modal-title"
                            aria-describedby="modal-modal-description"
                          >
                            <Box sx={style}>
                              <Typography
                                id="modal-modal-title"
                                variant="h6"
                                component="h2"
                                sx={{
                                  mr: 1,
                                  fontSize: "1rem",
                                  fontWeight: 300,
                                  color: theme.palette.primary.mainChannel,
                                }}
                              >
                                <b>EstacionAR - Comprobante de pago</b>
                              </Typography>
                              <Typography
                                id="modal-modal-description"
                                sx={{ mt: 2 }}
                              >
                                N??mero de recibo: <b>#{receipt.receiptId}</b>
                              </Typography>
                              <Typography
                                id="modal-modal-description"
                                sx={{ mt: 2 }}
                              >
                                N??mero de patente:{" "}
                                <b>{reservation.licensePlateOfDriver}</b>
                              </Typography>
                              <Typography
                                id="modal-modal-description"
                                sx={{ mt: 2 }}
                              >
                                Ubicaci??n:{" "}
                                <b>
                                  {reservation.location.streetName}{" "}
                                  {reservation.location.streetNumber}{" "}
                                </b>
                              </Typography>
                              <Typography
                                id="modal-modal-description"
                                sx={{ mt: 2 }}
                              >
                                Desde{" "}
                                <b>
                                  {reservation.timeFrame.startTime
                                    .split("T")
                                    .join(" ")}
                                </b>
                              </Typography>
                              <Typography
                                id="modal-modal-description"
                                sx={{ mt: 2 }}
                              >
                                Hasta{" "}
                                <b>
                                  {reservation.timeFrame.endTime
                                    .split("T")
                                    .join(" ")}
                                </b>
                              </Typography>
                              <Typography
                                id="modal-modal-description"
                                sx={{ mt: 2 }}
                              >
                                Importe: <b>${reservation.price}</b>
                              </Typography>
                              <Button
                                variant={!timeValue ? "contained" : "text"}
                                size="small"
                                sx={{
                                  color: theme.palette.action.active,
                                  left: 250,
                                  margin: 0,
                                }}
                                onClick={(e) => window.print()}
                              >
                                Imprimir
                              </Button>
                            </Box>
                          </Modal>
                        )}
                      </div>
                      <Button
                        onClick={handleOpen}
                        variant={!timeValue ? "contained" : "text"}
                        size="small"
                        sx={{ color: theme.palette.action.active }}
                      >
                        Ver Comprobante
                      </Button>
                    </Grid>
                  )}
                </Grid>
              </Grid>
              <Grid item sx={{ mb: 0 }}>
                <Grid container alignItems="center">
                  <Grid item xs={0}>
                    <Grid container alignItems="center">
                      <Grid direction={"row"}>
                        <Typography
                          sx={{
                            mr: 1,
                            fontSize: "1rem",
                            fontWeight: 200,
                            color: theme.palette.primary.mainChannel,
                          }}
                        >
                          <b>Precio</b> ${reservation.price}
                        </Typography>
                      </Grid>
                      <Grid item>
                        <Avatar
                          sx={{
                            ...theme.typography.smallAvatar,
                            cursor: "pointer",
                            backgroundColor: theme.palette.success.mainChannel,
                            color: theme.palette.primary.dark,
                          }}
                        >
                          <PaidIcon fontSize="inherit" />
                        </Avatar>
                      </Grid>
                      <Grid item xs={12} style={{ marginTop: 2 }}>
                        <Typography
                          sx={{
                            fontSize: "1rem",
                            fontWeight: 200,
                            color: theme.palette.primary.mainChannel,
                          }}
                        >
                          <b>Desde</b>{" "}
                          {reservation.timeFrame.startTime.split("T").join(" ")}
                        </Typography>
                        <Typography
                          sx={{
                            fontSize: "1rem",
                            fontWeight: 200,
                            color: theme.palette.primary.mainChannel,
                          }}
                        >
                          <b>Hasta</b>{" "}
                          {reservation.timeFrame.endTime.split("T").join(" ")}
                        </Typography>
                      </Grid>
                    </Grid>
                  </Grid>
                  <Grid item xs={12}>
                    <Box sx={{ p: 0 }}>
                      <List sx={{ py: 0 }}>
                        <ListItem
                          alignItems="center"
                          disableGutters
                          sx={{ py: 0 }}
                        >
                          <ListItemText
                            sx={{}}
                            primary={
                              <Typography
                                variant="h4"
                                sx={{
                                  fontSize: "1rem",
                                  fontWeight: 200,
                                  color:
                                    reservation.state === "PAID"
                                      ? theme.palette.primary.mainChannel
                                      : "#fff",
                                }}
                              >
                                <b>Estado</b>{" "}
                                {reservation.state === "PAID"
                                  ? "Pagado"
                                  : "Pendiente"}
                              </Typography>
                            }
                          />
                        </ListItem>
                      </List>
                    </Box>
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
            <Snackbar
              anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
              open={alertOpen}
              message={"I love snacks"}
              key={"bottom left"}
              autoHideDuration={10000}
              onClose={() => setAlertOpen(false)}
            >
              <Alert elevation={6} variant="filled" severity={severity}>
                {alertMsg}
                <Button
                  color="inherit"
                  size="small"
                  onClick={() => setAlertOpen(false)}
                >
                  Ok
                </Button>
              </Alert>
            </Snackbar>
          </Box>
        </CardWrapper>
      )}
    </>
  );
};

TotalOrderLineChartCard.propTypes = {
  isLoading: PropTypes.bool,
};

export default TotalOrderLineChartCard;
