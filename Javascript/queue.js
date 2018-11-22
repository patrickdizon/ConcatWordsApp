
class Queue {
    constructor() {
        this.data = [];
    }
  
    add (record) {
        this.data.unshift(record);
    }
    remove () {
        return this.data.pop();
    }

    first () {
        return this.data[0];
    }

    last () {
        return this.data[this.data.length - 1];
    }

    size () {
        return this.data.length;
    }
}

  
//const queue = new Queue();
module.exports = Queue