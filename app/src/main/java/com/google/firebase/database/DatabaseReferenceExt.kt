package com.google.firebase.database

import android.util.Log
import androidx.constraintlayout.solver.widgets.Snapshot
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.selfformat.yourrandomquote.data.FirebaseQuotesDataSource
import com.selfformat.yourrandomquote.domain.Quote
import java.util.concurrent.ExecutionException

fun DatabaseReference.users() : DatabaseReference {
    return child("users")
}

fun DatabaseReference.quotes() : DatabaseReference {
    return child("quotes")
}

fun DatabaseReference.name() : DatabaseReference {
    return child("name")
}

fun DatabaseReference.email() : DatabaseReference {
    return child("email")
}

fun DatabaseReference.withID(id: String) : DatabaseReference {
    return child(id)
}

fun DatabaseReference.getBlocking() : DataSnapshot {
    val taskSource = TaskCompletionSource<DataSnapshot>()
    addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            taskSource.setResult(dataSnapshot)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            taskSource.setException(databaseError.toException())
        }
    })
    val task = taskSource.task
    return try {
        Tasks.await(task)
        checkNotNull(task.result) {
            "task.result should not be bull"
        }
    } catch (ex: ExecutionException) {
        val exResult =  task.exception ?: ex
        throw exResult
    }
}
