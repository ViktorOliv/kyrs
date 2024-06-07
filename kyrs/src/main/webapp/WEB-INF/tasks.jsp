<!DOCTYPE html>
<html>
<head>
    <title>Task Scheduler</title>
    <script>
        // JavaScript for form validation and AJAX requests

        function validateForm() {
            let title = document.getElementById('title').value;
            let description = document.getElementById('description').value;
            let startTime = document.getElementById('startTime').value;
            let endTime = document.getElementById('endTime').value;
            let type = document.getElementById('type').value;

            if (title == "" || description == "" || startTime == "" || endTime == "" || type == "") {
                alert("All fields must be filled out");
                return false;
            }
            return true;
        }

        function addTask() {
            if (!validateForm()) {
                return;
            }

            let title = document.getElementById('title').value;
            let description = document.getElementById('description').value;
            let startTime = document.getElementById('startTime').value;
            let endTime = document.getElementById('endTime').value;
            let type = document.getElementById('type').value;

            let xhr = new XMLHttpRequest();
            xhr.open("POST", "tasks", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    document.getElementById('taskList').innerHTML = xhr.responseText;
                }
            };

            let data = "title=" + encodeURIComponent(title) +
                        "&description=" + encodeURIComponent(description) +
                        "&startTime=" + encodeURIComponent(startTime) +
                        "&endTime=" + encodeURIComponent(endTime) +
                        "&type=" + encodeURIComponent(type);
            xhr.send(data);
        }

        document.addEventListener("DOMContentLoaded", function() {
            document.getElementById('addTaskForm').addEventListener('submit', function(event) {
                event.preventDefault();
                addTask();
            });
        });
    </script>
</head>
<body>
    <h1>Task Scheduler</h1>
    <form id="addTaskForm">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required><br>

        <label for="description">Description:</label>
        <input type="text" id="description" name="description" required><br>

        <label for="startTime">Start Time:</label>
        <input type="datetime-local" id="startTime" name="startTime" required><br>

        <label for="endTime">End Time:</label>
        <input type="datetime-local" id="endTime" name="endTime" required><br>

        <label for="type">Type:</label>
        <input type="text" id="type" name="type" required><br>

        <input type="submit" value="Add Task">
    </form>

    <h2>Task List</h2>
    <ul id="taskList">
        <c:forEach var="task" items="${tasks}">
            <li>${task.title} - ${task.description} - ${task.startTime} to ${task.endTime} - ${task.type.name}</li>
        </c:forEach>
    </ul>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>
</body>
</html>
