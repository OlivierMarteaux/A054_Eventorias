// functions/index.js

const {
  onDocumentCreated,
  onDocumentDeleted,
} = require("firebase-functions/v2/firestore");

const {initializeApp} = require("firebase-admin/app");
const {getMessaging} = require("firebase-admin/messaging");
const {getStorage} = require("firebase-admin/storage");

// Initialize Firebase Admin SDK
initializeApp();

/**
 * Sends a notification to all users when a new post is created.
 * Uses a topic called "allUsers" — devices must subscribe to this topic.
 */
exports.sendNotificationOnPostCreated = onDocumentCreated(
    "posts/{postId}",
    async (event) => {
      try {
        const post = event.data.data();
        if (!post) {
          console.log("No post data found, skipping notification.");
          return;
        }

        // Build the notification payload
        const payload = {
          notification: {
            title: post.title || "New Post Created!",
            body: post.content || "Check out the latest post!",
          },
          topic: "allUsers", // Send to topic
        };

        // Send notification
        await getMessaging().send(payload);
        console.log(`Notification sent for post: ${post.title || post.id}`);
      } catch (error) {
        console.error("Error sending notification:", error);
      }
    },
);

/**
 * Deletes the image associated with a post when the document is deleted.
 */
exports.deletePostImage = onDocumentDeleted(
    "posts/{postId}",
    async (event) => {
      const data = event.data.data();
      if (!data) return;

      const photoUrl = data.photoUrl;
      if (!photoUrl) return;

      try {
        const bucket = getStorage().bucket();

        // Convert the full URL to Storage path
        const decodedUrl = decodeURIComponent(photoUrl);
        const splitIndex = decodedUrl.indexOf("/o/") + 3;
        const filePath = decodedUrl.substring(splitIndex).split("?")[0];

        console.log("Deleting file:", filePath);

        await bucket.file(filePath).delete();

        console.log("File deleted successfully.");
      } catch (error) {
        console.error("Error deleting file:", error);
      }
    },
);
