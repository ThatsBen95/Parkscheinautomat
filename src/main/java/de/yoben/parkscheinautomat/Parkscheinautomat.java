package de.yoben.parkscheinautomat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author Ben.Vielhaus
 *
 */
public class Parkscheinautomat
{

    /**
     * runs the parkscheinautomat program
     * @param args
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        long startedMillis;

        System.out.println("Schreibe \"START\" um ein Parkticket auszudrucken.");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("START"))
        {
            startedMillis = System.currentTimeMillis();
            System.out.println("Schreibe \"ENDE\" um zu bezahlen.");

            Scanner scannerToCheckOut = new Scanner(System.in);
            String checkOutInput = scannerToCheckOut.nextLine();

            if (checkOutInput.equalsIgnoreCase("ENDE"))
            {
                // parking time
                long endMillis = System.currentTimeMillis();
                long parkZeit = (endMillis - startedMillis);
                int parkedSeconds = (int) parkZeit / 1000;
                int parkedMinutes = 0;
                int parkedHours = 0;

                parkedMinutes = parkedSeconds / 60;
                parkedHours = parkedMinutes / 60;
                parkedSeconds %= 60;

                // parking costs
                int parkingCostEuros = 0;
                parkingCostEuros = calculateParkingCosts(2);

                System.out.println( "Du hast " +  parkedHours + " Stunde(n) " + parkedMinutes + " Minute(n) und " + parkedSeconds + " Sekunden geparkt.\n");

                if (isWeekend()) // if it is not Saturday or Sunday (weekend) then the payment procedure is started.
                {
                    if (parkingCostEuros > 0)
                    {
                        System.out.println("Du musst " +  parkingCostEuros + " Euro bezahlen.\n");
                        paymentProcess(parkingCostEuros);
                    }
                } else {
                    System.out.println("Am Wochenende ist parken kostenlos.");
                }

            } else {
                System.out.println("Ungültige Eingabe.");
            }
            scannerToCheckOut.close();
        } else {
            System.out.println("Ungültige Eingabe.");
        }
        System.exit(-1);
        scanner.close();
    }

    /**
     * this boolean checks if it is weekend for the calculation of the parking price
     * @return true if it is Saturday or Sunday (weekend day), otherwise false
     */
    private static boolean isWeekend()
    {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek day = currentDate.getDayOfWeek();
        String weekDayName = day.name();

        return weekDayName.equalsIgnoreCase("saturday") || weekDayName.equalsIgnoreCase("sunday");
    }

    /**
     * this method calculate the parking costs (parkingCost * parkedHours)
     * @param parkedHours to calculate the parking costs
     *
     * @return
     * ParkingCost in cents (int)
     */
    private static int calculateParkingCosts(int parkedHours)
    {
        // Figures in euros and minutes
        int parkingCostPerHour = 1; // euro

        int parkingCost = parkingCostPerHour * parkedHours;
        return parkingCost;
    }

    /**
     * this method deals with the purchase logic
     * @param priceToPay
     * the price you have to pay for an hour
     *
     */
    private static void paymentProcess(int priceToPay)
    {
        int paidMoney = 0;
        int cashBack = 0;
        boolean isCompletlyPaid = false;

        System.out.println("Bitte wirf dein Geld ein.");

        Scanner scannerToCheckInPayment = new Scanner(System.in);

        while (! isCompletlyPaid)
        {
            String moneyInput = scannerToCheckInPayment.nextLine();

            try
            {
                paidMoney =  Integer.parseInt(moneyInput);
            }
            catch (Exception e)
            {
                //e.printStackTrace();
                System.out.println("Bitte gebe einen gültigen Betrag an.");
                continue;
            }

            System.out.println("Du hast " + paidMoney + "€ eingeworfen.\n");

            // nicht genug geld
            if (paidMoney < priceToPay)
            {
                priceToPay -= paidMoney;
                System.out.println("Du musst noch " + priceToPay + " bezahlen.\n");
                continue;
            }

            // zu viel Geld,
            if (paidMoney > priceToPay)
            {
                cashBack = paidMoney - priceToPay;
                System.out.println("Du bekommst " + cashBack + "€ zurück.\n");
                isCompletlyPaid = true;
                break;
            }

            // passend bezahlt
            if (paidMoney == priceToPay)
            {
                System.out.println("Du hast passend bezahlt.\n");
                isCompletlyPaid = true;
                break;
            }
        }

        System.out.println("Bezahlvorgang abgeschlossen.");
        scannerToCheckInPayment.close();
    }
}
