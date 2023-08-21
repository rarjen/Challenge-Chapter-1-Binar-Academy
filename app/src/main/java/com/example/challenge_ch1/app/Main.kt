package com.example.challenge_ch1.app

import com.example.challenge_ch1.data.Food
import kotlinx.coroutines.*

var option = false;

fun displayMenu(list: List<Food>): Unit {
    val menu: String = """
        1. ${list[0].name} = Rp ${list[0].price}/porsi
        2. ${list[1].name} = Rp ${list[1].price}/porsi
        3. ${list[2].name} = Rp ${list[2].price}/porsi
        4. ${list[3].name} = Rp ${list[3].price}/porsi
        5. ${list[4].name} = Rp ${list[4].price}/porsi
    """.trimIndent()

    println(menu)
}

fun displayChoice(list: List<Food>, input: Int): Unit {
    val choice: String = """
        Kamu memilih menu 1
        Nama Menu : ${list[input - 1].name}
        Harga : Rp ${list[input - 1].price}
    """.trimIndent()

    println(choice)
}

fun displayDelivery(): Unit {
    val delivery = """
        1. Take Away
        2. Delivery
    """.trimIndent()

    println(delivery)
}

fun checkPayment(list: List<Food>, input: Int, payment: Int): Boolean {
    return payment >= list[input - 1].price
}

// function untuk melakukan print titik dengan menerima parameter delay dan jumlah dot
suspend fun printDots(delayMillis: Long, dotCount: Int) {
    repeat(dotCount) {
        print(".")
        delay(delayMillis)
    }
}

// Melakukan string concat antara pesan dan titik yg akan di print
suspend fun simulateCookingAndDelivery(duration: Long, message: String) {
    print("$message (5 detik) ")
    printDots(1000, 5) // Print titik dengan penundaan 1 detik
    println()
}


fun main() = runBlocking {
    do {
        try {
            val listFood: List<Food> = listOf<Food>(
                Food("Ayam Bakar", 50000),
                Food("Ayam Goreng", 40000),
                Food("Ayam Geprek", 40000),
                Food("Ayam Geprek", 40000),
                Food("Kulit Ayam Crispy", 15000),
                Food("Sate Usus Ayam", 5000)
            )

            // Display menu
            displayMenu(listFood);

            print("Masukkan Menu: ")

            // Input choice
            val choice: Int = readLine()!!.toInt();
            when(choice) {
                1 -> displayChoice(listFood, 1);
                2 -> displayChoice(listFood, 2);
                3 -> displayChoice(listFood, 3);
                4 -> displayChoice(listFood, 4);
                5 -> displayChoice(listFood, 5);
            }

            // Check Payment status
            print("Masukkan Pembayaran: ")
            val payment: Int = readln()!!.toInt();
            val status: Boolean = checkPayment(listFood, choice, payment);
            println(status)

            if (!status) {
                println("Maaf, pembayaran Anda gagal!");
                option = true;
            } else {
                println("Terima kasih, anda berhasil memesan makanan");

                // Pick Delivery
                displayDelivery();
                when (readln().toInt()) {
                    1 -> {
                        val job = launch {
                            simulateCookingAndDelivery(5000, "Makananmu sedang dimasak")
                            simulateCookingAndDelivery(
                                5000,
                                "Makananmu sudah siap! Silakan ambil di resto ya!"
                            )
                            print("Pesanan selesai! (3 detik) ")
                            printDots(1000, 3) // Print titik dengan penundaan 1 detik
                            println()
                        }
                        // memastikan  bahwa kode tidak melanjutkan ekseskusi sampai couroutine selesai
                        job.join();
                    }
                    2 -> {
                        val job = launch {
                            simulateCookingAndDelivery(5000, "Makananmu sedang dimasak")
                            simulateCookingAndDelivery(
                                5000,
                                "Makananmu sudah siap! Driver segera menuju tempatmu!"
                            )
                            print("Driver sampai! Pesanan selesai! (3 detik) ")
                            printDots(1000, 3) // Print titik dengan penundaan 1 detik
                            println()
                        }
                        // memastikan  bahwa kode tidak melanjutkan ekseskusi sampai couroutine selesai
                        job.join();
                    }

                }

                print("Ingin memesan lagi (Y/n)? ")
                when(readln()) {
                    "y" -> option = true;
                    "n" -> break;
                }
            }
        } catch (error: Throwable) {
            println("Error Pesan: Input tidak valid!");
            option = true;
        }
    } while (option)

    println("Program Selesai");
}