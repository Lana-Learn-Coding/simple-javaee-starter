<form method="post">
    <div class="form-group mb-2">
        <label for="name">Name</label>
        <input type="text" class="form-control input-name" id="name" name="name" readonly>
    </div>

    <div class="form-group mb-2">
        <label for="driver">Driver</label>
        <input type="text" class="form-control input-driver" id="driver" name="driver" readonly>
    </div>

    <div class="form-group mb-2">
        <label for="startDate">Start Date</label>
        <input type="date" class="form-control input-startDate" id="startDate"
               name="startDate" readonly>
    </div>

    <div class="form-group mb-2">
        <label for="numberOfPassenger">Number Of Passenger</label>
        <input type="number" step="1" min="0" class="form-control input-numberOfPassenger" id="numberOfPassenger"
               name="numberOfPassenger" readonly>
    </div>

    <div class="form-group mb-2">
        <label for="price">Price</label>
        <input type="number" step=".01" min="0" class="form-control input-price" id="price" name="price" readonly>
    </div>
</form>
