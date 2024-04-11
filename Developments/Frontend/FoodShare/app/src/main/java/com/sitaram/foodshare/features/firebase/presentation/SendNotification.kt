package com.sitaram.foodshare.features.firebase.presentation

class SendNotification {

//    suspend fun sendFCMNotification() {
//        val client = HttpClient(Android) {
//            install(JsonFeature) {
//                serializer = GsonSerializer()
//            }
//            defaultRequest {
//                header(HttpHeaders.ContentType, ContentType.Application.Json)
//                header(HttpHeaders.Authorization, "Bearer ya29.ElqKBGN2Ri_Uz...HnS_uNreA")
//            }
//        }
//
//        val fcmToken = "BIh4iVuDPTstO4aw08jAJvX6GgKfX1d4peuVYefu8pa0XjH-uNViQTh-MDJJCoudaa5mVPp1ZAiFacIv41LnjPY"
//        val message = mapOf("hello" to "This is a Firebase Cloud Messaging device group message!")
//
//        val fcmEndpoint = "https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send"
//
//        try {
//            val response = withContext(Dispatchers.IO) {
//                client.post<String>(fcmEndpoint) {
//                    body = mapOf("message" to mapOf("token" to fcmToken, "data" to message))
//                }
//            }
//            println("FCM Notification Sent. Response: $response")
//        } catch (e: ClientRequestException) {
//            println("Failed to send FCM Notification. Error: ${e.response.readText()}")
//        } finally {
//            client.close()
//        }
//    }
}