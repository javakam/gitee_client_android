package com.gitee.android.database

import android.content.Context
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Title: $
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/11/9  10:55
 */
// Import the Kotlin extensions for Realm.
import io.realm.kotlin.createObject
import io.realm.kotlin.where

// Define your model classes by extending RealmObject.
// You must annotate all classes with `open`.
open class Dog(
    // You can put properties in the constructor as long as
    // all of them are initialized with default values. This
    // ensures that an empty constructor is generated.
    // All properties are by default persisted.
    // Non-nullable properties must be initialized
    // with non-null values.
    var name: String = "",
    var age: Int = 0
) : RealmObject()

open class Person(
    // Properties can be annotated with PrimaryKey or Index.
    @PrimaryKey var id: Long = 0,
    var name: String = "",
    var age: Int = 0,

    // Other objects in a one-to-one relation must also subclass RealmObject.
    var dogs: RealmList<Dog> = RealmList()
) : RealmObject()

class Test {

    fun test(context: Context) {

        // Use Realm objects like regular Kotlin objects
        val dog = Dog()
        dog.name = "Rex"
        dog.age = 1

        // Initialize Realm (just once per application)
        Realm.init(context)

        // Get a Realm instance for this thread
        val realm = Realm.getDefaultInstance()

        // Query Realm for all dogs younger than 2 years old
        val puppies = realm.where<Dog>().lessThan("age", 2).findAll()
        puppies.size // => 0 because no dogs have been added to the Realm yet

        // Persist your data in a transaction
        realm.beginTransaction()
        val managedDog = realm.copyToRealm(dog) // Persist unmanaged objects
        val person = realm.createObject<Person>(0) // Create managed objects directly
        person.dogs.add(managedDog)
        realm.commitTransaction()

        // Listeners will be notified when data changes
        puppies.addChangeListener { results, changeSet ->
            // Query results are updated in real time with fine grained notifications.
            changeSet.insertions // => [0] is added.
        }

        // Asynchronously update objects on a background thread
        realm.executeTransactionAsync(Realm.Transaction { bgRealm ->
            // Find a dog to update.
            val dog = bgRealm.where<Dog>().equalTo("age", 1.toInt()).findFirst()!!
            dog.age = 3 // Update its age value.
        }, Realm.Transaction.OnSuccess {
            // Original queries and Realm objects are automatically updated.
            puppies.size // => 0 because there are no more puppies younger than 2 years old
            managedDog.age // => 3 the dogs age is updated
        })


    }
}