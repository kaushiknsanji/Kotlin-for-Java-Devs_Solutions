package taxipark

/**
 * Kotlin file to generate various statistics on the TaxiPark information
 * that contains registered drivers, passengers, and their trips.
 *
 * @author Kaushik N Sanji
 */

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        when {
            //When there are no trips, all drivers are fake drivers
            this.trips.isEmpty() -> this.allDrivers
            //When there are trips, eliminate the drivers (from the main Set) who have completed their trips
            else -> this.allDrivers.filter { driver: Driver ->
                !this.trips.map { it.driver }.any { it == driver }
            }.toSet()
        }

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        when {
            //When there are no trips, no Passengers would have taken a trip. Hence returning an empty Set
            this.trips.isEmpty() -> setOf()
            //When there are trips, consider only the Passengers (from the main Set) who have
            //completed the minimum number of trips
            else -> this.allPassengers.filter { passenger: Passenger ->
                this.trips.flatMap { it.passengers }.count { it == passenger } >= minTrips
            }.toSet()
        }

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        when {
            //When there are no trips, no Passengers would have taken a trip. Hence returning an empty Set
            this.trips.isEmpty() -> setOf()
            //When there are trips, consider only those Passengers (from the main Set) who have
            //completed more than 1 trip with the same Driver given
            else -> this.allPassengers.filter { passenger: Passenger ->
                this.trips.count { it.driver == driver && it.passengers.contains(passenger) } > 1
            }.toSet()
        }

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        when {
            //When there are no trips, no Passengers would have taken a trip. Hence returning an empty Set
            this.trips.isEmpty() -> setOf()
            //When there are trips, consider only those Passengers (from the main Set) who have
            //completed more trips with discount than without discount
            else -> this.allPassengers.filter { passenger: Passenger ->
                this.trips.count {
                    it.passengers.contains(passenger) && (it.discount ?: 0.0) > 0.0
                } > this.trips.count { it.passengers.contains(passenger) && (it.discount ?: 0.0).equals(0.0) }
            }.toSet()
        }

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return when {
        //When there are no trips, return 'null'
        this.trips.isEmpty() -> null
        //When there are trips
        else -> {
            //Derive a Map of period (duration-range) to their number of occurrence
            val periodCountMap = this.trips.map { IntRange(it.duration - it.duration % 10, (it.duration - it.duration % 10) + 9) }.groupBy { it }.mapValues { (_, list) -> list.size }
            //Return one duration-range that has occurred maximum number of times
            periodCountMap.filter { it.value == periodCountMap.values.max() }.keys.first()
        }
    }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    //Returning false when there are no trips since there will be 0 contribution
    if (this.trips.isEmpty()) return false
    //When there are trips
    //Derive a Map of Drivers to their total income (obtained from the cost of their trips)
    val driversIncomeMap = this.trips.groupBy { it.driver }.mapValues { (_, trips) -> trips.sumByDouble { it.cost } }
    //From the Map of Drivers-Income, calculate the total income from all Drivers
    val allDriversTotalIncome = driversIncomeMap.values.sum()
    //Derive a Map of Drivers to their Percentage Contribution (towards total income) from the Map of Drivers-Income
    val driversContributionMap = driversIncomeMap.map { it.key to it.value / allDriversTotalIncome }.toMap()
    //From the total number of Drivers, get the number of drivers required to meet the top 20% drivers contributing 80% of the total income
    val minTopDriversCount = Math.floor(this.allDrivers.count() * 0.2).toInt()
    //Evaluate and return if the minimum number of top contributors are contributing at least 80% of the total income
    return driversContributionMap.values.sortedDescending().take(minTopDriversCount).sum() >= 0.8
}