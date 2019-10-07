function comfort() {
  let a = document.getElementsByClassName('advanced-params ');
  return (a.length) ? document.getElementsByClassName('advanced-params ')[0].innerText : null;
}

t = {
       avitoId:document.getElementsByClassName('b-search-map expanded item-map-wrapper js-item-map-wrapper')[0].getAttribute('data-item-id'),
       description:document.getElementsByClassName('item-description')[0].innerText,
       phone:document.getElementsByClassName('item-phone-number js-item-phone-number')[0] && document.getElementsByClassName('item-phone-number js-item-phone-number')[0].children[0].children[0].src,
       photos:[...document.getElementsByClassName('gallery-extended-img-frame js-gallery-extended-img-frame')].map(e => e.getAttribute('data-url').slice(2)),
       comfort:comfort(),
       name:document.getElementsByClassName('title-info-title-text')[0].innerText,
       price:document.getElementsByClassName('price-value-string js-price-value-string')[0].innerText,
       //deposite:document.getElementsByClassName('item-price-sub-price')[0].innerText,
       address:document.getElementsByClassName('item-address__string')[0].innerText,
       params:document.getElementsByClassName('item-params-list')[0].innerText,
       subway:document.getElementsByClassName('item-address-georeferences')[0].innerText,
       map:document.getElementsByClassName('b-search-map expanded item-map-wrapper js-item-map-wrapper')[0].getAttribute('data-map-lat') + ',' + document.getElementsByClassName('b-search-map expanded item-map-wrapper js-item-map-wrapper')[0].getAttribute('data-map-lon')
   }
   
return JSON.stringify(t);
   